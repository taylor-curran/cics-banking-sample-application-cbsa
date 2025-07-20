package com.cbsa.migration.service;

import com.cbsa.migration.dto.AccountDeleteRequestDto;
import com.cbsa.migration.dto.AccountDeleteResponseDto;
import com.cbsa.migration.dto.AccountInquiryRequestDto;
import com.cbsa.migration.dto.AccountInquiryResponseDto;
import com.cbsa.migration.dto.DisplayAccountRequestDto;
import com.cbsa.migration.dto.DisplayAccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayAccountService {

    @Autowired
    private AccountInquiryService accountInquiryService;

    @Autowired
    private AccountDeleteService accountDeleteService;

    public DisplayAccountResponseDto displayAccount(DisplayAccountRequestDto request) {
        
        if (!isValidAccountNumber(request.getAccountNumber())) {
            return new DisplayAccountResponseDto(false, "Please enter an account number.");
        }

        AccountInquiryRequestDto inquiryRequest = new AccountInquiryRequestDto();
        inquiryRequest.setAccountNumber(request.getAccountNumber());

        AccountInquiryResponseDto inquiryResponse = accountInquiryService.inquireAccount(inquiryRequest);

        if (!inquiryResponse.isSuccess()) {
            return new DisplayAccountResponseDto(false, inquiryResponse.getMessage());
        }

        DisplayAccountResponseDto response = new DisplayAccountResponseDto(true, "Account found");
        response.setSortCode(inquiryResponse.getSortCode());
        response.setCustomerNumber(inquiryResponse.getCustomerNumber());
        response.setAccountNumber(inquiryResponse.getAccountNumber());
        response.setAccountType(inquiryResponse.getAccountType());
        response.setInterestRate(inquiryResponse.getInterestRate());
        response.setDateOpened(inquiryResponse.getDateOpened());
        response.setOverdraftLimit(inquiryResponse.getOverdraftLimit());
        response.setLastStatementDate(inquiryResponse.getLastStatementDate());
        response.setNextStatementDate(inquiryResponse.getNextStatementDate());
        response.setAvailableBalance(inquiryResponse.getAvailableBalance());
        response.setActualBalance(inquiryResponse.getActualBalance());

        return response;
    }

    public DisplayAccountResponseDto deleteAccount(DisplayAccountRequestDto request) {
        
        if (!isValidAccountNumber(request.getAccountNumber())) {
            return new DisplayAccountResponseDto(false, "Please enter an account number.");
        }

        AccountDeleteRequestDto deleteRequest = new AccountDeleteRequestDto();
        deleteRequest.setAccountNumber(request.getAccountNumber());

        AccountDeleteResponseDto deleteResponse = accountDeleteService.deleteAccount(deleteRequest);

        if (!deleteResponse.isSuccess()) {
            String errorMessage = getDeleteErrorMessage(deleteResponse.getFailureCode());
            return new DisplayAccountResponseDto(false, errorMessage);
        }

        DisplayAccountResponseDto response = new DisplayAccountResponseDto(true, "Account successfully deleted");
        response.setDeleted(true);
        response.setSortCode(deleteResponse.getSortCode());

        return response;
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

    private String getDeleteErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Sorry, but that account number was not found. Account NOT deleted.";
            case "2":
                return "Sorry, but a datastore error occurred. Account NOT deleted.";
            case "3":
                return "Sorry, but a delete error occurred. Account NOT deleted.";
            default:
                return "Sorry, but a delete error occurred. Account NOT deleted.";
        }
    }
}
