package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class DisplayCustomerRequestDto {
    
    @NotBlank(message = "Customer number is required")
    private String customerNumber;
    
    private String action;
    private String sortCode;

    public DisplayCustomerRequestDto() {}

    public DisplayCustomerRequestDto(String customerNumber, String action) {
        this.customerNumber = customerNumber;
        this.action = action;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
}
