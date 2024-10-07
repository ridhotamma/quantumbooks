package com.quantumbooks.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class JournalEntryLineDto {
    private Long lineId;

    @NotNull(message = "Journal Entry ID is required")
    private Long journalEntryId;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Debit amount is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Debit amount must be non-negative")
    @Digits(integer = 13, fraction = 2, message = "Debit amount must have at most 13 integer digits and 2 fraction digits")
    private BigDecimal debit;

    @NotNull(message = "Credit amount is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Credit amount must be non-negative")
    @Digits(integer = 13, fraction = 2, message = "Credit amount must have at most 13 integer digits and 2 fraction digits")
    private BigDecimal credit;
}