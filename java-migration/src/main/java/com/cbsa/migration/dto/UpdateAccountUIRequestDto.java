package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class UpdateAccountUIRequestDto {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    private String customerNumber;
    private String sortCode;
    private String accountType;
    private String interestRate;
    private String overdraftLimit;
    private String availableBalance;
    private String actualBalance;
    private String lastStatementDate;
    private String nextStatementDate;

    public UpdateAccountUIRequestDto() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(String overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(String actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getLastStatementDate() {
        return lastStatementDate;
    }

    public void setLastStatementDate(String lastStatementDate) {
        this.lastStatementDate = lastStatementDate;
    }

    public String getNextStatementDate() {
        return nextStatementDate;
    }

    public void setNextStatementDate(String nextStatementDate) {
        this.nextStatementDate = nextStatementDate;
    }
}
