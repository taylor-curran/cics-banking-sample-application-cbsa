package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class DisplayAccountRequestDto {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    private String action;

    public DisplayAccountRequestDto() {}

    public DisplayAccountRequestDto(String accountNumber, String action) {
        this.accountNumber = accountNumber;
        this.action = action;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
