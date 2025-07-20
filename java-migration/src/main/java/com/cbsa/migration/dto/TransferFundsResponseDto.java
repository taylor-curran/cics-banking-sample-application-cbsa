package com.cbsa.migration.dto;

import java.math.BigDecimal;

public class TransferFundsResponseDto {
    
    private boolean success;
    private String message;
    private String failureCode;
    private BigDecimal fromAvailableBalance;
    private BigDecimal fromActualBalance;
    private BigDecimal toAvailableBalance;
    private BigDecimal toActualBalance;

    public TransferFundsResponseDto() {}

    public TransferFundsResponseDto(boolean success, String message) {
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

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public BigDecimal getFromAvailableBalance() {
        return fromAvailableBalance;
    }

    public void setFromAvailableBalance(BigDecimal fromAvailableBalance) {
        this.fromAvailableBalance = fromAvailableBalance;
    }

    public BigDecimal getFromActualBalance() {
        return fromActualBalance;
    }

    public void setFromActualBalance(BigDecimal fromActualBalance) {
        this.fromActualBalance = fromActualBalance;
    }

    public BigDecimal getToAvailableBalance() {
        return toAvailableBalance;
    }

    public void setToAvailableBalance(BigDecimal toAvailableBalance) {
        this.toAvailableBalance = toAvailableBalance;
    }

    public BigDecimal getToActualBalance() {
        return toActualBalance;
    }

    public void setToActualBalance(BigDecimal toActualBalance) {
        this.toActualBalance = toActualBalance;
    }
}
