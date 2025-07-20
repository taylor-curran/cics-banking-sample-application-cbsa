package com.cbsa.migration.service;

import com.cbsa.migration.dto.TransferFundsRequestDto;
import com.cbsa.migration.dto.TransferFundsResponseDto;
import com.cbsa.migration.model.Account;
import com.cbsa.migration.model.ProcessedTransaction;
import com.cbsa.migration.repository.AccountRepository;
import com.cbsa.migration.repository.ProcessedTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TransferFundsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProcessedTransactionRepository processedTransactionRepository;

    @Transactional
    public TransferFundsResponseDto transferFunds(TransferFundsRequestDto request) {
        
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            TransferFundsResponseDto response = new TransferFundsResponseDto(false, "Transfer amount must be positive");
            response.setFailureCode("4");
            return response;
        }

        if (request.getFromAccountNumber().equals(request.getToAccountNumber()) && 
            request.getFromSortCode().equals(request.getToSortCode())) {
            TransferFundsResponseDto response = new TransferFundsResponseDto(false, "Cannot transfer to the same account");
            response.setFailureCode("5");
            return response;
        }

        Optional<Account> fromAccountOpt = accountRepository.findByAccountNumber(request.getFromAccountNumber().toString());
        if (!fromAccountOpt.isPresent()) {
            TransferFundsResponseDto response = new TransferFundsResponseDto(false, "From account not found");
            response.setFailureCode("1");
            return response;
        }
        Account fromAccount = fromAccountOpt.get();

        Optional<Account> toAccountOpt = accountRepository.findByAccountNumber(request.getToAccountNumber().toString());
        if (!toAccountOpt.isPresent()) {
            TransferFundsResponseDto response = new TransferFundsResponseDto(false, "To account not found");
            response.setFailureCode("1");
            return response;
        }
        Account toAccount = toAccountOpt.get();

        BigDecimal newFromAvailableBalance = fromAccount.getAvailableBalance().subtract(request.getAmount());
        BigDecimal newFromActualBalance = fromAccount.getActualBalance().subtract(request.getAmount());
        BigDecimal newToAvailableBalance = toAccount.getAvailableBalance().add(request.getAmount());
        BigDecimal newToActualBalance = toAccount.getActualBalance().add(request.getAmount());

        fromAccount.setAvailableBalance(newFromAvailableBalance);
        fromAccount.setActualBalance(newFromActualBalance);
        toAccount.setAvailableBalance(newToAvailableBalance);
        toAccount.setActualBalance(newToActualBalance);

        try {
            accountRepository.updateAccount(fromAccount);
            accountRepository.updateAccount(toAccount);

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String transactionRef = generateTransactionReference();

            ProcessedTransaction fromTransaction = new ProcessedTransaction();
            fromTransaction.setSortCode(fromAccount.getSortCode());
            fromTransaction.setAccountNumber(fromAccount.getAccountNumber());
            fromTransaction.setTransactionDate(currentDate);
            fromTransaction.setTransactionTime(currentTime);
            fromTransaction.setTransactionReference(transactionRef);
            fromTransaction.setTransactionType("TFR");
            fromTransaction.setTransactionDescription("Transfer to " + toAccount.getAccountNumber());
            fromTransaction.setTransactionAmount(request.getAmount().negate());

            ProcessedTransaction toTransaction = new ProcessedTransaction();
            toTransaction.setSortCode(toAccount.getSortCode());
            toTransaction.setAccountNumber(toAccount.getAccountNumber());
            toTransaction.setTransactionDate(currentDate);
            toTransaction.setTransactionTime(currentTime);
            toTransaction.setTransactionReference(transactionRef);
            toTransaction.setTransactionType("TFR");
            toTransaction.setTransactionDescription("Transfer from " + fromAccount.getAccountNumber());
            toTransaction.setTransactionAmount(request.getAmount());

            processedTransactionRepository.insertTransaction(fromTransaction);
            processedTransactionRepository.insertTransaction(toTransaction);

            TransferFundsResponseDto response = new TransferFundsResponseDto(true, "Transfer completed successfully");
            response.setFromAvailableBalance(newFromAvailableBalance);
            response.setFromActualBalance(newFromActualBalance);
            response.setToAvailableBalance(newToAvailableBalance);
            response.setToActualBalance(newToActualBalance);

            return response;

        } catch (Exception e) {
            TransferFundsResponseDto response = new TransferFundsResponseDto(false, "Transfer failed due to system error");
            response.setFailureCode("2");
            return response;
        }
    }

    private String generateTransactionReference() {
        return "TXN" + System.currentTimeMillis();
    }
}
