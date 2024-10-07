package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TaxRateDto {
    private Long taxRateId;

    @NotBlank(message = "Tax name is required")
    @Size(max = 100, message = "Tax name must not exceed 100 characters")
    private String taxName;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.00", message = "Rate must be greater than or equal to 0.00")
    @DecimalMax(value = "100.00", message = "Rate must be less than or equal to 100.00")
    private BigDecimal rate;

    @NotNull(message = "Effective date is required")
    @FutureOrPresent(message = "Effective date must be in the present or future")
    private LocalDate effectiveDate;

    @NotNull(message = "Status is required")
    private TaxStatus status;

    public enum TaxStatus {
        ACTIVE, INACTIVE, EXPIRED
    }
}