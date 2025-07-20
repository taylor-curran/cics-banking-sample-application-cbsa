package com.cbsa.migration.service;

import com.cbsa.migration.dto.CreditDebitRequestDto;
import com.cbsa.migration.dto.CreditDebitResponseDto;
import com.cbsa.migration.dto.DebitCreditRequestDto;
import com.cbsa.migration.dto.DebitCreditResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditDebitService {

    @Autowired
    private DebitCreditService debitCreditService;

    @Autowired
    private SortCodeService sortCodeService;

    public CreditDebitResponseDto processCreditDebit(CreditDebitRequestDto request) {
        
        if (!isValidAccountNumber(request.getAccountNumber())) {
            return new CreditDebitResponseDto(false, "Please enter an account number.");
        }

        if (isZeroAccountNumber(request.getAccountNumber())) {
            return new CreditDebitResponseDto(false, "Please enter a non zero account number.");
        }

        if (!isValidSign(request.getSign())) {
            return new CreditDebitResponseDto(false, "Please enter + or - preceding the amount");
        }

        if (!isValidAmount(request.getAmount())) {
            return new CreditDebitResponseDto(false, "The Amount entered must be numeric.");
        }

        if (isZeroAmount(request.getAmount())) {
            return new CreditDebitResponseDto(false, "Please supply a non-zero amount.");
        }

        BigDecimal finalAmount = request.getAmount();
        if ("-".equals(request.getSign())) {
            finalAmount = finalAmount.negate();
        }

        String sortCode = sortCodeService.getSortCode();

        DebitCreditRequestDto debitCreditRequest = new DebitCreditRequestDto(
            request.getAccountNumber(), finalAmount, sortCode);

        DebitCreditResponseDto debitCreditResponse = debitCreditService.processDebitCredit(debitCreditRequest);

        if (!debitCreditResponse.isSuccess()) {
            String errorMessage = getErrorMessage(debitCreditResponse.getFailureCode());
            CreditDebitResponseDto response = new CreditDebitResponseDto(false, errorMessage);
            response.setFailureCode(debitCreditResponse.getFailureCode());
            return response;
        }

        String successMessage = "+".equals(request.getSign()) ? 
            "Credit successful" : "Debit successful";

        return new CreditDebitResponseDto(true, successMessage, 
            debitCreditResponse.getAvailableBalance(), debitCreditResponse.getActualBalance());
    }

    private boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(accountNumber.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isZeroAccountNumber(String accountNumber) {
        try {
            return Long.parseLong(accountNumber.trim()) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidSign(String sign) {
        return "+".equals(sign) || "-".equals(sign);
    }

    private boolean isValidAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isZeroAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) == 0;
    }

    private String getErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Account not found";
            case "2":
                return "Database error occurred";
            case "3":
                return "Insufficient funds";
            case "4":
                return "Operation not allowed for this account type";
            default:
                return "Transaction failed";
        }
    }
}
