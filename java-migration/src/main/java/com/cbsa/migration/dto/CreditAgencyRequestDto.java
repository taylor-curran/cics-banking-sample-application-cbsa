package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreditAgencyRequestDto {
    
    @NotBlank(message = "Sort code is required")
    private String sortCode;
    
    @NotBlank(message = "Customer number is required")
    private String customerNumber;
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    private String customerAddress;
    private String dateOfBirth;
    private Integer currentCreditScore;
    private String creditScoreReviewDate;

    public CreditAgencyRequestDto() {}

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getCurrentCreditScore() {
        return currentCreditScore;
    }

    public void setCurrentCreditScore(Integer currentCreditScore) {
        this.currentCreditScore = currentCreditScore;
    }

    public String getCreditScoreReviewDate() {
        return creditScoreReviewDate;
    }

    public void setCreditScoreReviewDate(String creditScoreReviewDate) {
        this.creditScoreReviewDate = creditScoreReviewDate;
    }
}
