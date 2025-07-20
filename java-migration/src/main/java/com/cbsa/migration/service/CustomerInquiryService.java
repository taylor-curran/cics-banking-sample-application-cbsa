package com.cbsa.migration.service;

import com.cbsa.migration.dto.CustomerInquiryRequestDto;
import com.cbsa.migration.dto.CustomerInquiryResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerInquiryService {

    public CustomerInquiryResponseDto inquireCustomer(CustomerInquiryRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new CustomerInquiryResponseDto(false, validationError);
        }

        try {
            CustomerInquiryResponseDto response = new CustomerInquiryResponseDto(true, "Customer inquiry completed successfully");
            response.setCustomerNumber(request.getCustomerNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerName("Sample Customer Name");
            response.setCustomerAddress("Sample Address");
            response.setDateOfBirth("01011990");
            response.setCreditScore(750);
            response.setCreditScoreReviewDate(getCurrentDate());
            
            return response;
            
        } catch (Exception e) {
            return new CustomerInquiryResponseDto(false, "Error during customer inquiry: " + e.getMessage());
        }
    }

    private String validateRequest(CustomerInquiryRequestDto request) {
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

    private String getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
        return currentDate.format(formatter);
    }
}
