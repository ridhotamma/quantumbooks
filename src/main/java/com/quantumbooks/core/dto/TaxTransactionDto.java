package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TaxTransactionDto {
    private Long transactionId;

    @NotNull(message = "Tax rate ID is required")
    private Long taxRateId;

    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date must be in the past or present")
    private LocalDate transactionDate;

    @NotNull(message = "Taxable amount is required")
    @DecimalMin(value = "0.01", message = "Taxable amount must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Taxable amount must have at most 13 digits in total and 2 decimal places")
    private BigDecimal taxableAmount;

    @NotNull(message = "Tax amount is required")
    @DecimalMin(value = "0.00", message = "Tax amount must be greater than or equal to 0")
    @Digits(integer = 13, fraction = 2, message = "Tax amount must have at most 13 digits in total and 2 decimal places")
    private BigDecimal taxAmount;

    @NotNull(message = "Status is required")
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING, COMPLETED, CANCELLED
    }
}