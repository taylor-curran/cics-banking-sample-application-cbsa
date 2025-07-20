package com.cbsa.migration.service;

import com.cbsa.migration.dto.CreditCardServicesRequestDto;
import com.cbsa.migration.dto.CreditCardServicesResponseDto;
import com.cbsa.migration.dto.CustomerCreationRequestDto;
import com.cbsa.migration.dto.CustomerCreationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CreditCardServicesService {

    @Autowired
    private CustomerCreationService customerCreationService;

    private static final List<String> VALID_TITLES = Arrays.asList(
        "Mr", "Mrs", "Miss", "Ms", "Dr", "Professor", "Drs", "Lord", "Sir", "Lady"
    );

    public CreditCardServicesResponseDto createCustomerForCreditCard(CreditCardServicesRequestDto request) {
        
        String normalizedTitle = normalizeTitle(request.getCustomerTitle());
        if (normalizedTitle == null) {
            return new CreditCardServicesResponseDto(false, 
                "Valid titles are: Mr,Mrs,Miss,Ms,Dr,Professor,Drs,Lord,Sir,Lady");
        }

        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            return new CreditCardServicesResponseDto(false, "Missing expected data.");
        }

        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            return new CreditCardServicesResponseDto(false, "Missing expected data.");
        }

        if (request.getAddressLine1() == null || request.getAddressLine1().trim().isEmpty()) {
            return new CreditCardServicesResponseDto(false, "Missing expected data.");
        }

        if (request.getBirthDay() == null || request.getBirthMonth() == null || request.getBirthYear() == null) {
            return new CreditCardServicesResponseDto(false, "Missing expected data.");
        }

        String fullName = buildFullName(normalizedTitle, request.getFirstName(), 
                                      request.getMiddleName(), request.getLastName());
        String fullAddress = buildFullAddress(request.getAddressLine1(), 
                                            request.getAddressLine2(), request.getAddressLine3());

        CustomerCreationRequestDto customerRequest = new CustomerCreationRequestDto();
        customerRequest.setName(fullName);
        customerRequest.setAddress(fullAddress);
        customerRequest.setBirthDay(request.getBirthDay());
        customerRequest.setBirthMonth(request.getBirthMonth());
        customerRequest.setBirthYear(request.getBirthYear());

        CustomerCreationResponseDto customerResponse = customerCreationService.createCustomer(customerRequest);

        if (!customerResponse.isSuccess()) {
            String errorMessage = "Sorry but unable to create Customer record ";
            if ("O".equals(customerResponse.getFailureCode())) {
                errorMessage = "Sorry, customer is too old. Please check D.O.B.";
            } else if ("Y".equals(customerResponse.getFailureCode())) {
                errorMessage = "Sorry, customer D.O.B. is in the future.";
            } else if ("Z".equals(customerResponse.getFailureCode())) {
                errorMessage = "Sorry, customer D.O.B. is invalid.";
            }
            
            CreditCardServicesResponseDto response = new CreditCardServicesResponseDto(false, errorMessage);
            response.setFailureCode(customerResponse.getFailureCode());
            return response;
        }

        CreditCardServicesResponseDto response = new CreditCardServicesResponseDto(
            true, 
            "The Customer record has been successfully created",
            customerResponse.getSortCode(),
            customerResponse.getCustomerNumber(),
            customerResponse.getCreditScore(),
            customerResponse.getCreditScoreReviewDate()
        );

        return response;
    }

    private String normalizeTitle(String title) {
        if (title == null) {
            return null;
        }

        String cleanTitle = title.replace("_", " ").trim();
        
        for (String validTitle : VALID_TITLES) {
            if (cleanTitle.equalsIgnoreCase(validTitle)) {
                return validTitle;
            }
        }
        
        return null;
    }

    private String buildFullName(String title, String firstName, String middleName, String lastName) {
        StringBuilder nameBuilder = new StringBuilder();
        
        if (title != null && !title.trim().isEmpty()) {
            nameBuilder.append(title.replace("_", " ").trim()).append(" ");
        }
        
        if (firstName != null && !firstName.trim().isEmpty()) {
            nameBuilder.append(firstName.replace("_", " ").trim()).append(" ");
        }
        
        if (middleName != null && !middleName.trim().isEmpty()) {
            nameBuilder.append(middleName.replace("_", " ").trim()).append(" ");
        }
        
        if (lastName != null && !lastName.trim().isEmpty()) {
            nameBuilder.append(lastName.replace("_", " ").trim());
        }
        
        return nameBuilder.toString().trim();
    }

    private String buildFullAddress(String line1, String line2, String line3) {
        StringBuilder addressBuilder = new StringBuilder();
        
        if (line1 != null && !line1.trim().isEmpty()) {
            addressBuilder.append(line1.replace("_", " ").trim());
        }
        
        if (line2 != null && !line2.trim().isEmpty()) {
            if (addressBuilder.length() > 0) {
                addressBuilder.append(" ");
            }
            addressBuilder.append(line2.replace("_", " ").trim());
        }
        
        if (line3 != null && !line3.trim().isEmpty()) {
            if (addressBuilder.length() > 0) {
                addressBuilder.append(" ");
            }
            addressBuilder.append(line3.replace("_", " ").trim());
        }
        
        return addressBuilder.toString();
    }
}
