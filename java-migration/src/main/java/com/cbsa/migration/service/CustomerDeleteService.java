package com.cbsa.migration.service;

import com.cbsa.migration.dto.CustomerDeleteRequestDto;
import com.cbsa.migration.dto.CustomerDeleteResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerDeleteService {

    public CustomerDeleteResponseDto deleteCustomer(CustomerDeleteRequestDto request) {
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new CustomerDeleteResponseDto(false, validationError);
        }

        try {
            CustomerDeleteResponseDto response = new CustomerDeleteResponseDto(true, "Customer deleted successfully");
            response.setCustomerNumber(request.getCustomerNumber());
            response.setSortCode(request.getSortCode());
            response.setCustomerName("Deleted Customer");
            
            return response;
            
        } catch (Exception e) {
            return new CustomerDeleteResponseDto(false, "Error during customer deletion: " + e.getMessage());
        }
    }

    private String validateRequest(CustomerDeleteRequestDto request) {
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
