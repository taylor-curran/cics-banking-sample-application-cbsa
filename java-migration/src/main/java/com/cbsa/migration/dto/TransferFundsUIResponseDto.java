package com.cbsa.migration.dto;

import java.math.BigDecimal;

public class TransferFundsUIResponseDto {
    
    private boolean success;
    private String message;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal transferAmount;
    private BigDecimal fromAvailableBalance;
    private BigDecimal fromActualBalance;
    private BigDecimal toAvailableBalance;
    private BigDecimal toActualBalance;

    public TransferFundsUIResponseDto() {}

    public TransferFundsUIResponseDto(boolean success, String message) {
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

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
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
