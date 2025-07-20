package com.cbsa.migration.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProcessedTransaction {
    
    private String eyecatcher;
    private String sortCode;
    private String accountNumber;
    private LocalDate date;
    private LocalTime time;
    private String reference;
    private String type;
    private String description;
    private BigDecimal amount;

    public ProcessedTransaction() {}

    public ProcessedTransaction(String sortCode, String accountNumber, String reference, 
                              String type, String description, BigDecimal amount) {
        this.eyecatcher = "PRTR";
        this.sortCode = sortCode;
        this.accountNumber = accountNumber;
        this.reference = reference;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public String getEyecatcher() {
        return eyecatcher;
    }

    public void setEyecatcher(String eyecatcher) {
        this.eyecatcher = eyecatcher;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionDate(String dateStr) {
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                this.date = LocalDate.parse(dateStr);
            } catch (Exception e) {
                this.date = LocalDate.now();
            }
        } else {
            this.date = LocalDate.now();
        }
    }

    public void setTransactionTime(String timeStr) {
        if (timeStr != null && !timeStr.trim().isEmpty()) {
            try {
                this.time = LocalTime.parse(timeStr);
            } catch (Exception e) {
                this.time = LocalTime.now();
            }
        } else {
            this.time = LocalTime.now();
        }
    }

    public void setTransactionReference(String reference) {
        this.reference = reference;
    }

    public void setTransactionType(String type) {
        this.type = type;
    }

    public void setTransactionDescription(String description) {
        this.description = description;
    }

    public void setTransactionAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
