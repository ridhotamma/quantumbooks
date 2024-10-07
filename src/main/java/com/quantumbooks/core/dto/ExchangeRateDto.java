package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExchangeRateDto {
    private Integer exchangeRateId;

    @NotNull(message = "From currency ID is required")
    private Integer fromCurrencyId;

    @NotNull(message = "To currency ID is required")
    private Integer toCurrencyId;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.000001", message = "Rate must be greater than 0")
    @Digits(integer = 4, fraction = 6, message = "Rate must have at most 4 digits before the decimal and 6 after")
    private BigDecimal rate;

    @NotNull(message = "Effective date is required")
    private LocalDate effectiveDate;
}