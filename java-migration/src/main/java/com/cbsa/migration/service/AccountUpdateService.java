package com.cbsa.migration.service;

import com.cbsa.migration.dto.AccountUpdateRequestDto;
import com.cbsa.migration.dto.AccountUpdateResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AccountUpdateService {

    public AccountUpdateResponseDto updateAccount(AccountUpdateRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new AccountUpdateResponseDto(false, validationError);
        }

        try {
            AccountUpdateResponseDto response = new AccountUpdateResponseDto(true, "Account updated successfully");
            response.setAccountNumber(request.getAccountNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerNumber(request.getCustomerNumber());
            response.setAccountType(request.getAccountType());
            response.setAvailableBalance(request.getAvailableBalance());
            response.setActualBalance(request.getActualBalance());
            response.setLastStatementDate(request.getLastStatementDate());
            response.setNextStatementDate(request.getNextStatementDate());
            
            return response;
            
        } catch (Exception e) {
            return new AccountUpdateResponseDto(false, "Error during account update: " + e.getMessage());
        }
    }

    private String validateRequest(AccountUpdateRequestDto request) {
        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            return "Account number is required";
        }
        
        try {
            Long.parseLong(request.getAccountNumber().trim());
        } catch (NumberFormatException e) {
            return "Invalid account number format";
        }
        
        return null;
    }
}
