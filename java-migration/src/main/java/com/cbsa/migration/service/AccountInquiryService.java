package com.cbsa.migration.service;

import com.cbsa.migration.dto.AccountInquiryRequestDto;
import com.cbsa.migration.dto.AccountInquiryResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountInquiryService {

    public AccountInquiryResponseDto inquireAccount(AccountInquiryRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new AccountInquiryResponseDto(false, validationError);
        }

        try {
            AccountInquiryResponseDto response = new AccountInquiryResponseDto(true, "Account inquiry completed successfully");
            response.setAccountNumber(request.getAccountNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerNumber("123456");
            response.setAccountType("CURRENT");
            response.setAvailableBalance(new BigDecimal("1500.00"));
            response.setActualBalance(new BigDecimal("1500.00"));
            response.setLastStatementDate(getCurrentDate());
            response.setNextStatementDate(getNextMonthDate());
            
            return response;
            
        } catch (Exception e) {
            return new AccountInquiryResponseDto(false, "Error during account inquiry: " + e.getMessage());
        }
    }

    private String validateRequest(AccountInquiryRequestDto request) {
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

    private String getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
        return currentDate.format(formatter);
    }

    private String getNextMonthDate() {
        java.time.LocalDate nextMonth = java.time.LocalDate.now().plusMonths(1);
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
        return nextMonth.format(formatter);
    }
}
