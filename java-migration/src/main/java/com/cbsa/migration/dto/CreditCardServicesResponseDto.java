package com.cbsa.migration.dto;

public class CreditCardServicesResponseDto {
    
    private boolean success;
    private String message;
    private String sortCode;
    private String customerNumber;
    private Integer creditScore;
    private String creditScoreReviewDate;
    private String failureCode;

    public CreditCardServicesResponseDto() {}

    public CreditCardServicesResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CreditCardServicesResponseDto(boolean success, String message, String sortCode, 
                                       String customerNumber, Integer creditScore, 
                                       String creditScoreReviewDate) {
        this.success = success;
        this.message = message;
        this.sortCode = sortCode;
        this.customerNumber = customerNumber;
        this.creditScore = creditScore;
        this.creditScoreReviewDate = creditScoreReviewDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getCreditScoreReviewDate() {
        return creditScoreReviewDate;
    }

    public void setCreditScoreReviewDate(String creditScoreReviewDate) {
        this.creditScoreReviewDate = creditScoreReviewDate;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }
}
