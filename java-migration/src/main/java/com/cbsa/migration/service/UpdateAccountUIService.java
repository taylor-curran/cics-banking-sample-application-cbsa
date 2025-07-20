package com.cbsa.migration.service;

import com.cbsa.migration.dto.AccountInquiryRequestDto;
import com.cbsa.migration.dto.AccountInquiryResponseDto;
import com.cbsa.migration.dto.AccountUpdateRequestDto;
import com.cbsa.migration.dto.AccountUpdateResponseDto;
import com.cbsa.migration.dto.UpdateAccountUIRequestDto;
import com.cbsa.migration.dto.UpdateAccountUIResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UpdateAccountUIService {

    @Autowired
    private AccountInquiryService accountInquiryService;

    @Autowired
    private AccountUpdateService accountUpdateService;

    public UpdateAccountUIResponseDto inquireAccount(UpdateAccountUIRequestDto request) {
        
        String validationError = validateAccountNumber(request.getAccountNumber());
        if (validationError != null) {
            return new UpdateAccountUIResponseDto(false, validationError);
        }

        AccountInquiryRequestDto inquiryRequest = new AccountInquiryRequestDto();
        inquiryRequest.setAccountNumber(request.getAccountNumber());

        AccountInquiryResponseDto inquiryResponse = accountInquiryService.inquireAccount(inquiryRequest);

        if (!inquiryResponse.isSuccess()) {
            return new UpdateAccountUIResponseDto(false, "Account not found");
        }

        UpdateAccountUIResponseDto response = new UpdateAccountUIResponseDto(true, "Account found");
        response.setAccountNumber(inquiryResponse.getAccountNumber());
        response.setCustomerNumber(inquiryResponse.getCustomerNumber());
        response.setSortCode(inquiryResponse.getSortCode());
        response.setAccountType(inquiryResponse.getAccountType());
        response.setInterestRate(inquiryResponse.getInterestRate());
        response.setOverdraftLimit(inquiryResponse.getOverdraftLimit());
        response.setAvailableBalance(inquiryResponse.getAvailableBalance());
        response.setActualBalance(inquiryResponse.getActualBalance());
        response.setLastStatementDate(inquiryResponse.getLastStatementDate());
        response.setNextStatementDate(inquiryResponse.getNextStatementDate());

        return response;
    }

    public UpdateAccountUIResponseDto updateAccount(UpdateAccountUIRequestDto request) {
        
        String validationError = validateUpdateData(request);
        if (validationError != null) {
            return new UpdateAccountUIResponseDto(false, validationError);
        }

        AccountUpdateRequestDto updateRequest = new AccountUpdateRequestDto();
        updateRequest.setAccountNumber(request.getAccountNumber());
        updateRequest.setCustomerNumber(request.getCustomerNumber());
        updateRequest.setSortCode(request.getSortCode());
        updateRequest.setAccountType(request.getAccountType());
        
        if (request.getInterestRate() != null && !request.getInterestRate().trim().isEmpty()) {
            updateRequest.setInterestRate(new BigDecimal(request.getInterestRate().trim()));
        }
        
        if (request.getOverdraftLimit() != null && !request.getOverdraftLimit().trim().isEmpty()) {
            updateRequest.setOverdraftLimit(new BigDecimal(request.getOverdraftLimit().trim()));
        }
        
        if (request.getAvailableBalance() != null && !request.getAvailableBalance().trim().isEmpty()) {
            updateRequest.setAvailableBalance(new BigDecimal(request.getAvailableBalance().trim()));
        }
        
        if (request.getActualBalance() != null && !request.getActualBalance().trim().isEmpty()) {
            updateRequest.setActualBalance(new BigDecimal(request.getActualBalance().trim()));
        }
        
        updateRequest.setLastStatementDate(request.getLastStatementDate());
        updateRequest.setNextStatementDate(request.getNextStatementDate());

        AccountUpdateResponseDto updateResponse = accountUpdateService.updateAccount(updateRequest);

        if (!updateResponse.isSuccess()) {
            String errorMessage = getUpdateErrorMessage(updateResponse.getFailureCode());
            return new UpdateAccountUIResponseDto(false, errorMessage);
        }

        UpdateAccountUIResponseDto response = new UpdateAccountUIResponseDto(true, "Account successfully updated");
        response.setAccountNumber(updateResponse.getAccountNumber());
        response.setCustomerNumber(updateResponse.getCustomerNumber());
        response.setSortCode(updateResponse.getSortCode());
        response.setAccountType(updateResponse.getAccountType());
        response.setInterestRate(updateResponse.getInterestRate());
        response.setOverdraftLimit(updateResponse.getOverdraftLimit());
        response.setAvailableBalance(updateResponse.getAvailableBalance());
        response.setActualBalance(updateResponse.getActualBalance());
        response.setLastStatementDate(updateResponse.getLastStatementDate());
        response.setNextStatementDate(updateResponse.getNextStatementDate());

        return response;
    }

    public UpdateAccountUIResponseDto enableUpdateMode(UpdateAccountUIRequestDto request) {
        
        UpdateAccountUIResponseDto inquiryResponse = inquireAccount(request);
        if (!inquiryResponse.isSuccess()) {
            return inquiryResponse;
        }

        inquiryResponse.setUpdateMode(true);
        inquiryResponse.setMessage("Amend data then press <ENTER>.");

        return inquiryResponse;
    }

    private String validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return "Please enter an account number.";
        }
        
        try {
            Long.parseLong(accountNumber.trim());
            return null;
        } catch (NumberFormatException e) {
            return "Please enter an account number.";
        }
    }

    private String validateUpdateData(UpdateAccountUIRequestDto request) {
        
        String accountValidation = validateAccountNumber(request.getAccountNumber());
        if (accountValidation != null) {
            return accountValidation;
        }

        if (request.getInterestRate() != null && !request.getInterestRate().trim().isEmpty()) {
            try {
                new BigDecimal(request.getInterestRate().trim());
            } catch (NumberFormatException e) {
                return "Please enter a valid interest rate.";
            }
        }

        if (request.getOverdraftLimit() != null && !request.getOverdraftLimit().trim().isEmpty()) {
            try {
                new BigDecimal(request.getOverdraftLimit().trim());
            } catch (NumberFormatException e) {
                return "Please enter a valid overdraft limit.";
            }
        }

        if (request.getAvailableBalance() != null && !request.getAvailableBalance().trim().isEmpty()) {
            try {
                new BigDecimal(request.getAvailableBalance().trim());
            } catch (NumberFormatException e) {
                return "Please enter a valid available balance.";
            }
        }

        if (request.getActualBalance() != null && !request.getActualBalance().trim().isEmpty()) {
            try {
                new BigDecimal(request.getActualBalance().trim());
            } catch (NumberFormatException e) {
                return "Please enter a valid actual balance.";
            }
        }

        return null;
    }

    private String getUpdateErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Sorry, but that account number was not found. Account NOT updated.";
            case "2":
                return "Sorry, but a datastore error occurred. Account NOT updated.";
            case "3":
                return "Sorry, but an update error occurred. Account NOT updated.";
            default:
                return "Sorry, but an update error occurred. Account NOT updated.";
        }
    }
}
