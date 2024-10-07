package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BankTransactionDto {
    private Long transactionId;

    @NotNull(message = "Bank account ID is required")
    private Long bankAccountId;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Amount must have at most 13 integer digits and 2 fraction digits")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    private Boolean reconciled;

    public enum TransactionType {
        DEBIT, CREDIT
    }
}