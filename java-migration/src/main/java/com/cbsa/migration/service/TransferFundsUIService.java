package com.cbsa.migration.service;

import com.cbsa.migration.dto.TransferFundsRequestDto;
import com.cbsa.migration.dto.TransferFundsResponseDto;
import com.cbsa.migration.dto.TransferFundsUIRequestDto;
import com.cbsa.migration.dto.TransferFundsUIResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferFundsUIService {

    @Autowired
    private TransferFundsService transferFundsService;

    @Autowired
    private SortCodeService sortCodeService;

    public TransferFundsUIResponseDto processTransfer(TransferFundsUIRequestDto request) {
        
        String validationError = validateInput(request);
        if (validationError != null) {
            return new TransferFundsUIResponseDto(false, validationError);
        }

        try {
            Long fromAccountNumber = Long.parseLong(request.getFromAccountNumber().trim());
            Long toAccountNumber = Long.parseLong(request.getToAccountNumber().trim());
            BigDecimal amount = parseAmount(request.getAmount());

            String sortCode = sortCodeService.getSortCode();
            Long sortCodeLong = Long.parseLong(sortCode);

            TransferFundsRequestDto transferRequest = new TransferFundsRequestDto(
                fromAccountNumber, sortCodeLong, toAccountNumber, sortCodeLong, amount);

            TransferFundsResponseDto transferResponse = transferFundsService.transferFunds(transferRequest);

            TransferFundsUIResponseDto response = new TransferFundsUIResponseDto(
                transferResponse.isSuccess(), transferResponse.getMessage());

            if (transferResponse.isSuccess()) {
                response.setFromAccountNumber(request.getFromAccountNumber());
                response.setToAccountNumber(request.getToAccountNumber());
                response.setTransferAmount(amount);
                response.setFromAvailableBalance(transferResponse.getFromAvailableBalance());
                response.setFromActualBalance(transferResponse.getFromActualBalance());
                response.setToAvailableBalance(transferResponse.getToAvailableBalance());
                response.setToActualBalance(transferResponse.getToActualBalance());
            } else {
                response.setMessage(getErrorMessage(transferResponse.getFailureCode()));
            }

            return response;

        } catch (Exception e) {
            return new TransferFundsUIResponseDto(false, "Invalid input format: " + e.getMessage());
        }
    }

    private String validateInput(TransferFundsUIRequestDto request) {
        
        if (request.getFromAccountNumber() == null || request.getFromAccountNumber().trim().isEmpty()) {
            return "Please enter a FROM account no";
        }

        if (request.getToAccountNumber() == null || request.getToAccountNumber().trim().isEmpty()) {
            return "Please enter a TO account no";
        }

        if (request.getAmount() == null || request.getAmount().trim().isEmpty()) {
            return "Please enter an amount";
        }

        try {
            Long.parseLong(request.getFromAccountNumber().trim());
        } catch (NumberFormatException e) {
            return "Please enter a FROM account no";
        }

        try {
            Long.parseLong(request.getToAccountNumber().trim());
        } catch (NumberFormatException e) {
            return "Please enter a TO account no";
        }

        if (request.getFromAccountNumber().trim().equals(request.getToAccountNumber().trim())) {
            return "The FROM & TO account should be different";
        }

        if ("00000000".equals(request.getFromAccountNumber().trim()) || 
            "00000000".equals(request.getToAccountNumber().trim())) {
            return "Account no 00000000 is not valid";
        }

        try {
            BigDecimal amount = parseAmount(request.getAmount());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return "Amount must be greater than zero";
            }
        } catch (Exception e) {
            return "Please enter a valid amount";
        }

        return null;
    }

    private BigDecimal parseAmount(String amountStr) {
        String cleanAmount = amountStr.trim().replace(",", "");
        
        if (cleanAmount.contains(".")) {
            return new BigDecimal(cleanAmount);
        } else {
            return new BigDecimal(cleanAmount).divide(new BigDecimal("100"));
        }
    }

    private String getErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Account not found";
            case "2":
                return "System error occurred during transfer";
            case "4":
                return "Transfer amount must be positive";
            case "5":
                return "Cannot transfer to the same account";
            default:
                return "Transfer failed";
        }
    }
}
