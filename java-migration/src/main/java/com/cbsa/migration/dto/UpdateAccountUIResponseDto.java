package com.cbsa.migration.dto;

import java.math.BigDecimal;

public class UpdateAccountUIResponseDto {
    
    private boolean success;
    private String message;
    private String accountNumber;
    private String customerNumber;
    private String sortCode;
    private String accountType;
    private BigDecimal interestRate;
    private BigDecimal overdraftLimit;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private String lastStatementDate;
    private String nextStatementDate;
    private boolean updateMode;

    public UpdateAccountUIResponseDto() {}

    public UpdateAccountUIResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
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

    public boolean isUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }
}
