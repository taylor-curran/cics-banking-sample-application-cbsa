package com.cbsa.migration.service;

import com.cbsa.migration.dto.CustomerDeleteRequestDto;
import com.cbsa.migration.dto.CustomerDeleteResponseDto;
import com.cbsa.migration.dto.CustomerInquiryRequestDto;
import com.cbsa.migration.dto.CustomerInquiryResponseDto;
import com.cbsa.migration.dto.CustomerUpdateRequestDto;
import com.cbsa.migration.dto.CustomerUpdateResponseDto;
import com.cbsa.migration.dto.DisplayCustomerRequestDto;
import com.cbsa.migration.dto.DisplayCustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayCustomerService {

    @Autowired
    private CustomerInquiryService customerInquiryService;

    @Autowired
    private CustomerDeleteService customerDeleteService;

    @Autowired
    private CustomerUpdateService customerUpdateService;

    public DisplayCustomerResponseDto displayCustomer(DisplayCustomerRequestDto request) {
        
        if (!isValidCustomerNumber(request.getCustomerNumber())) {
            return new DisplayCustomerResponseDto(false, "Please enter a customer number.");
        }

        CustomerInquiryRequestDto inquiryRequest = new CustomerInquiryRequestDto();
        inquiryRequest.setCustomerNumber(request.getCustomerNumber());
        inquiryRequest.setSortCode(request.getSortCode());

        CustomerInquiryResponseDto inquiryResponse = customerInquiryService.inquireCustomer(inquiryRequest);

        if (!inquiryResponse.isSuccess()) {
            return new DisplayCustomerResponseDto(false, "Sorry, but that customer number was not found.");
        }

        DisplayCustomerResponseDto response = new DisplayCustomerResponseDto(true, "Customer found");
        response.setSortCode(inquiryResponse.getSortCode());
        response.setCustomerNumber(inquiryResponse.getCustomerNumber());
        response.setCustomerName(inquiryResponse.getCustomerName());
        response.setAddress1(inquiryResponse.getAddress1());
        response.setAddress2(inquiryResponse.getAddress2());
        response.setAddress3(inquiryResponse.getAddress3());
        response.setDateOfBirth(inquiryResponse.getDateOfBirth());
        response.setCreditScore(inquiryResponse.getCreditScore());
        response.setCreditScoreReviewDate(inquiryResponse.getCreditScoreReviewDate());

        return response;
    }

    public DisplayCustomerResponseDto deleteCustomer(DisplayCustomerRequestDto request) {
        
        if (!isValidCustomerNumber(request.getCustomerNumber())) {
            return new DisplayCustomerResponseDto(false, "Please enter a customer number.");
        }

        CustomerDeleteRequestDto deleteRequest = new CustomerDeleteRequestDto();
        deleteRequest.setCustomerNumber(request.getCustomerNumber());

        CustomerDeleteResponseDto deleteResponse = customerDeleteService.deleteCustomer(deleteRequest);

        if (!deleteResponse.isSuccess()) {
            String errorMessage = getDeleteErrorMessage(deleteResponse.getFailureCode());
            return new DisplayCustomerResponseDto(false, errorMessage);
        }

        DisplayCustomerResponseDto response = new DisplayCustomerResponseDto(true, "Customer successfully deleted");
        response.setDeleted(true);

        return response;
    }

    public DisplayCustomerResponseDto updateCustomer(DisplayCustomerRequestDto request) {
        
        if (!isValidCustomerNumber(request.getCustomerNumber())) {
            return new DisplayCustomerResponseDto(false, "Please enter a customer number.");
        }

        CustomerUpdateRequestDto updateRequest = new CustomerUpdateRequestDto();
        updateRequest.setCustomerNumber(request.getCustomerNumber());

        CustomerUpdateResponseDto updateResponse = customerUpdateService.updateCustomer(updateRequest);

        if (!updateResponse.isSuccess()) {
            String errorMessage = getUpdateErrorMessage(updateResponse.getFailureCode());
            return new DisplayCustomerResponseDto(false, errorMessage);
        }

        DisplayCustomerResponseDto response = new DisplayCustomerResponseDto(true, "Customer successfully updated");
        response.setSortCode(updateResponse.getSortCode());
        response.setCustomerNumber(updateResponse.getCustomerNumber());
        response.setCustomerName(updateResponse.getCustomerName());
        response.setAddress1(updateResponse.getAddress1());
        response.setAddress2(updateResponse.getAddress2());
        response.setAddress3(updateResponse.getAddress3());
        response.setDateOfBirth(updateResponse.getDateOfBirth());
        response.setCreditScore(updateResponse.getCreditScore());
        response.setCreditScoreReviewDate(updateResponse.getCreditScoreReviewDate());

        return response;
    }

    public DisplayCustomerResponseDto enableUpdateMode(DisplayCustomerRequestDto request) {
        
        DisplayCustomerResponseDto displayResponse = displayCustomer(request);
        if (!displayResponse.isSuccess()) {
            return displayResponse;
        }

        displayResponse.setUpdateMode(true);
        displayResponse.setMessage("Amend data then press <ENTER>.");

        return displayResponse;
    }

    private boolean isValidCustomerNumber(String customerNumber) {
        if (customerNumber == null || customerNumber.trim().isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(customerNumber.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getDeleteErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Sorry, but that customer number was not found. Customer NOT deleted.";
            case "2":
                return "Sorry, but a datastore error occurred. Customer NOT deleted.";
            case "3":
                return "Sorry, but a delete error occurred. Customer NOT deleted.";
            default:
                return "Sorry, but a delete error occurred. Customer NOT deleted.";
        }
    }

    private String getUpdateErrorMessage(String failureCode) {
        switch (failureCode) {
            case "1":
                return "Sorry, but that customer number was not found. Customer NOT updated.";
            case "2":
                return "Sorry, but a datastore error occurred. Customer NOT updated.";
            case "3":
                return "Sorry, but an update error occurred. Customer NOT updated.";
            default:
                return "Sorry, but an update error occurred. Customer NOT updated.";
        }
    }
}
