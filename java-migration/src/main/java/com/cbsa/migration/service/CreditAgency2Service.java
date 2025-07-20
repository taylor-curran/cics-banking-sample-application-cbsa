package com.cbsa.migration.service;

import com.cbsa.migration.dto.CreditAgencyRequestDto;
import com.cbsa.migration.dto.CreditAgencyResponseDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CreditAgency2Service {

    private static final String AGENCY_ID = "CRDTAGY2";
    private static final int MIN_DELAY_MS = 0;
    private static final int MAX_DELAY_MS = 3000;
    private static final int MIN_CREDIT_SCORE = 1;
    private static final int MAX_CREDIT_SCORE = 999;

    public CreditAgencyResponseDto processCreditScoring(CreditAgencyRequestDto request) {
        
        long startTime = System.currentTimeMillis();
        
        String validationError = validateRequest(request);
        if (validationError != null) {
            return new CreditAgencyResponseDto(false, validationError);
        }

        try {
            simulateProcessingDelay();
            
            int newCreditScore = generateRandomCreditScore();
            
            CreditAgencyResponseDto response = new CreditAgencyResponseDto(true, "Credit scoring completed successfully");
            response.setSortCode(request.getSortCode());
            response.setCustomerNumber(request.getCustomerNumber());
            response.setCustomerName(request.getCustomerName());
            response.setCustomerAddress(request.getCustomerAddress());
            response.setDateOfBirth(request.getDateOfBirth());
            response.setNewCreditScore(newCreditScore);
            response.setCreditScoreReviewDate(getCurrentDate());
            response.setAgencyId(AGENCY_ID);
            response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            
            return response;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new CreditAgencyResponseDto(false, "Credit scoring process was interrupted");
        } catch (Exception e) {
            return new CreditAgencyResponseDto(false, "Error during credit scoring: " + e.getMessage());
        }
    }

    public CreditAgencyResponseDto getAgencyInfo() {
        CreditAgencyResponseDto response = new CreditAgencyResponseDto(true, "Credit Agency 2 Information");
        response.setAgencyId(AGENCY_ID);
        response.setMessage("Dummy credit agency for credit scoring simulation");
        return response;
    }

    private String validateRequest(CreditAgencyRequestDto request) {
        if (request.getSortCode() == null || request.getSortCode().trim().isEmpty()) {
            return "Sort code is required";
        }
        
        if (request.getCustomerNumber() == null || request.getCustomerNumber().trim().isEmpty()) {
            return "Customer number is required";
        }
        
        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            return "Customer name is required";
        }
        
        try {
            Long.parseLong(request.getSortCode().trim());
        } catch (NumberFormatException e) {
            return "Invalid sort code format";
        }
        
        try {
            Long.parseLong(request.getCustomerNumber().trim());
        } catch (NumberFormatException e) {
            return "Invalid customer number format";
        }
        
        return null;
    }

    private void simulateProcessingDelay() throws InterruptedException {
        int delayMs = ThreadLocalRandom.current().nextInt(MIN_DELAY_MS, MAX_DELAY_MS + 1);
        Thread.sleep(delayMs);
    }

    private int generateRandomCreditScore() {
        return ThreadLocalRandom.current().nextInt(MIN_CREDIT_SCORE, MAX_CREDIT_SCORE + 1);
    }

    private String getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
        return currentDate.format(formatter);
    }
}
