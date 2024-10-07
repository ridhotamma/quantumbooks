package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CurrencyDto {
    private Integer currencyId;

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters")
    private String currencyCode;

    @NotBlank(message = "Currency name is required")
    @Size(max = 50, message = "Currency name must not exceed 50 characters")
    private String currencyName;
}