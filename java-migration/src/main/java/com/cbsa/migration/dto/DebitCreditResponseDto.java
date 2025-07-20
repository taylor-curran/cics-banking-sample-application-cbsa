package com.cbsa.migration.dto;

import java.math.BigDecimal;

public class DebitCreditResponseDto {
    
    private boolean success;
    private String failureCode;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private String message;

    public DebitCreditResponseDto() {}

    public DebitCreditResponseDto(boolean success, String failureCode) {
        this.success = success;
        this.failureCode = failureCode;
    }

    public DebitCreditResponseDto(boolean success, BigDecimal availableBalance, BigDecimal actualBalance) {
        this.success = success;
        this.availableBalance = availableBalance;
        this.actualBalance = actualBalance;
        this.failureCode = "0";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
