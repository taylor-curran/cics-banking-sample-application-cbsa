package com.cbsa.migration.service;

import com.cbsa.migration.dto.CustomerCreationRequestDto;
import com.cbsa.migration.dto.CustomerCreationResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerCreationService {

    public CustomerCreationResponseDto createCustomer(CustomerCreationRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new CustomerCreationResponseDto(false, validationError);
        }

        try {
            String newCustomerNumber = generateCustomerNumber();
            
            CustomerCreationResponseDto response = new CustomerCreationResponseDto(true, "Customer created successfully");
            response.setCustomerNumber(newCustomerNumber);
            response.setSortCode(request.getSortCode());
            response.setCustomerName(request.getCustomerName());
            response.setCustomerAddress(request.getCustomerAddress());
            response.setDateOfBirth(request.getDateOfBirth());
            response.setCreditScore(request.getCreditScore() != null ? request.getCreditScore() : 500);
            response.setCreditScoreReviewDate(getCurrentDate());
            
            return response;
            
        } catch (Exception e) {
            return new CustomerCreationResponseDto(false, "Error during customer creation: " + e.getMessage());
        }
    }

    private String validateRequest(CustomerCreationRequestDto request) {
        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            return "Customer name is required";
        }
        
        if (request.getCustomerAddress() == null || request.getCustomerAddress().trim().isEmpty()) {
            return "Customer address is required";
        }
        
        if (request.getDateOfBirth() == null || request.getDateOfBirth().trim().isEmpty()) {
            return "Date of birth is required";
        }
        
        return null;
    }

    private String generateCustomerNumber() {
        return String.valueOf(System.currentTimeMillis() % 1000000);
    }

    private String getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
        return currentDate.format(formatter);
    }
}
