package com.cbsa.migration.dto;

import java.math.BigDecimal;

public class CreditDebitResponseDto {
    
    private boolean success;
    private String message;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private String failureCode;

    public CreditDebitResponseDto() {}

    public CreditDebitResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CreditDebitResponseDto(boolean success, String message, BigDecimal availableBalance, BigDecimal actualBalance) {
        this.success = success;
        this.message = message;
        this.availableBalance = availableBalance;
        this.actualBalance = actualBalance;
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

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }
}
