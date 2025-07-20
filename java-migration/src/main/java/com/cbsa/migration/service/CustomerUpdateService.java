package com.cbsa.migration.service;

import com.cbsa.migration.dto.CustomerUpdateRequestDto;
import com.cbsa.migration.dto.CustomerUpdateResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerUpdateService {

    public CustomerUpdateResponseDto updateCustomer(CustomerUpdateRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new CustomerUpdateResponseDto(false, validationError);
        }

        try {
            CustomerUpdateResponseDto response = new CustomerUpdateResponseDto(true, "Customer updated successfully");
            response.setCustomerNumber(request.getCustomerNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerName(request.getCustomerName());
            response.setCustomerAddress(request.getCustomerAddress());
            response.setDateOfBirth(request.getDateOfBirth());
            response.setCreditScore(request.getCreditScore());
            response.setCreditScoreReviewDate(request.getCreditScoreReviewDate());
            
            return response;
            
        } catch (Exception e) {
            return new CustomerUpdateResponseDto(false, "Error during customer update: " + e.getMessage());
        }
    }

    private String validateRequest(CustomerUpdateRequestDto request) {
        if (request.getCustomerNumber() == null || request.getCustomerNumber().trim().isEmpty()) {
            return "Customer number is required";
        }
        
        try {
            Long.parseLong(request.getCustomerNumber().trim());
        } catch (NumberFormatException e) {
            return "Invalid customer number format";
        }
        
        return null;
    }
}
