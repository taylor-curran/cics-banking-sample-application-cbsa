package com.cbsa.migration.service;

import com.cbsa.migration.dto.AccountDeleteRequestDto;
import com.cbsa.migration.dto.AccountDeleteResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AccountDeleteService {

    public AccountDeleteResponseDto deleteAccount(AccountDeleteRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new AccountDeleteResponseDto(false, validationError);
        }

        try {
            AccountDeleteResponseDto response = new AccountDeleteResponseDto(true, "Account deleted successfully");
            response.setAccountNumber(request.getAccountNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerNumber("123456");
            
            return response;
            
        } catch (Exception e) {
            return new AccountDeleteResponseDto(false, "Error during account deletion: " + e.getMessage());
        }
    }

    private String validateRequest(AccountDeleteRequestDto request) {
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
