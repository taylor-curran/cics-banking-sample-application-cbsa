package com.cbsa.migration.service;

import com.cbsa.migration.dto.DebitCreditRequestDto;
import com.cbsa.migration.dto.DebitCreditResponseDto;
import com.cbsa.migration.model.Account;
import com.cbsa.migration.model.ProcessedTransaction;
import com.cbsa.migration.repository.AccountRepository;
import com.cbsa.migration.repository.ProcessedTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class DebitCreditService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProcessedTransactionRepository processedTransactionRepository;

    @Autowired
    private ErrorLoggingService errorLoggingService;

    @Transactional
    public DebitCreditResponseDto processDebitCredit(DebitCreditRequestDto request) {
        
        try {
            Optional<Account> accountOpt = accountRepository.findBySortCodeAndAccountNumber(
                request.getSortCode(), request.getAccountNumber());

            if (!accountOpt.isPresent()) {
                return new DebitCreditResponseDto(false, "1");
            }
            Account account = accountOpt.get();

            if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                DebitCreditResponseDto validationResult = validateDebitTransaction(account, request);
                if (!validationResult.isSuccess()) {
                    return validationResult;
                }
            } else {
                DebitCreditResponseDto validationResult = validateCreditTransaction(account, request);
                if (!validationResult.isSuccess()) {
                    return validationResult;
                }
            }

            BigDecimal newAvailableBalance = account.getAvailableBalance().add(request.getAmount());
            BigDecimal newActualBalance = account.getActualBalance().add(request.getAmount());

            account.setAvailableBalance(newAvailableBalance);
            account.setActualBalance(newActualBalance);

            accountRepository.updateAccount(account);

            writeTransactionRecord(account, request);

            return new DebitCreditResponseDto(true, newAvailableBalance, newActualBalance);

        } catch (Exception e) {
            errorLoggingService.logError("DBCRFUN", e);
            return new DebitCreditResponseDto(false, "2");
        }
    }

    private DebitCreditResponseDto validateDebitTransaction(Account account, DebitCreditRequestDto request) {
        
        if (("MORTGAGE".equals(account.getAccountType()) || "LOAN".equals(account.getAccountType().trim())) 
            && (request.getFacilityType() == null || request.getFacilityType() == 496)) {
            return new DebitCreditResponseDto(false, "4");
        }

        BigDecimal difference = account.getAvailableBalance().add(request.getAmount());
        if (difference.compareTo(BigDecimal.ZERO) < 0 && 
            (request.getFacilityType() == null || request.getFacilityType() == 496)) {
            return new DebitCreditResponseDto(false, "3");
        }

        return new DebitCreditResponseDto(true, "0");
    }

    private DebitCreditResponseDto validateCreditTransaction(Account account, DebitCreditRequestDto request) {
        
        if (("MORTGAGE".equals(account.getAccountType()) || "LOAN".equals(account.getAccountType().trim())) 
            && (request.getFacilityType() == null || request.getFacilityType() == 496)) {
            return new DebitCreditResponseDto(false, "4");
        }

        return new DebitCreditResponseDto(true, "0");
    }

    private void writeTransactionRecord(Account account, DebitCreditRequestDto request) {
        String transactionType;
        String description;

        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            if (request.getFacilityType() != null && request.getFacilityType() == 496) {
                transactionType = "PDR";
                description = buildOriginDescription(request);
            } else {
                transactionType = "DEB";
                description = "COUNTER WTHDRW";
            }
        } else {
            if (request.getFacilityType() != null && request.getFacilityType() == 496) {
                transactionType = "PCR";
                description = buildOriginDescription(request);
            } else {
                transactionType = "CRE";
                description = "COUNTER RECVED";
            }
        }

        String reference = UUID.randomUUID().toString().substring(0, 12);

        ProcessedTransaction transaction = new ProcessedTransaction(
            account.getSortCode(),
            account.getAccountNumber(),
            reference,
            transactionType,
            description,
            request.getAmount()
        );

        processedTransactionRepository.save(transaction);
    }

    private String buildOriginDescription(DebitCreditRequestDto request) {
        StringBuilder description = new StringBuilder();
        
        if (request.getApplId() != null) {
            description.append(request.getApplId().length() > 8 ? 
                request.getApplId().substring(0, 8) : request.getApplId());
        }
        
        if (request.getUserId() != null && description.length() < 14) {
            int remaining = 14 - description.length();
            String userId = request.getUserId().length() > remaining ? 
                request.getUserId().substring(0, remaining) : request.getUserId();
            description.append(userId);
        }
        
        return description.toString();
    }
}
