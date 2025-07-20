package com.cbsa.migration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferFundsRequestDto {
    
    @NotNull(message = "From account number is required")
    private Long fromAccountNumber;
    
    @NotNull(message = "From sort code is required")
    private Long fromSortCode;
    
    @NotNull(message = "To account number is required")
    private Long toAccountNumber;
    
    @NotNull(message = "To sort code is required")
    private Long toSortCode;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    public TransferFundsRequestDto() {}

    public TransferFundsRequestDto(Long fromAccountNumber, Long fromSortCode, 
                                   Long toAccountNumber, Long toSortCode, BigDecimal amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.fromSortCode = fromSortCode;
        this.toAccountNumber = toAccountNumber;
        this.toSortCode = toSortCode;
        this.amount = amount;
    }

    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getFromSortCode() {
        return fromSortCode;
    }

    public void setFromSortCode(Long fromSortCode) {
        this.fromSortCode = fromSortCode;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Long getToSortCode() {
        return toSortCode;
    }

    public void setToSortCode(Long toSortCode) {
        this.toSortCode = toSortCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
