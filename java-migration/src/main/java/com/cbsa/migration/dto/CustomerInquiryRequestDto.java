package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class CustomerInquiryRequestDto {
    
    @NotBlank(message = "Customer number is required")
    private String customerNumber;
    
    private String sortCode;

    public CustomerInquiryRequestDto() {}

    public CustomerInquiryRequestDto(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
}
