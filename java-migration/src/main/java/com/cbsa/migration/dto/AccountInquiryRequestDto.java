package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class AccountInquiryRequestDto {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    private String sortCode;

    public AccountInquiryRequestDto() {}

    public AccountInquiryRequestDto(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
}
