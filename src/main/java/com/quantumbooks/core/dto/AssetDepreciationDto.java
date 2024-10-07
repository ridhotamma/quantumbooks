package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AssetDepreciationDto {
    private Long depreciationId;

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Depreciation date is required")
    @PastOrPresent(message = "Depreciation date must be in the past or present")
    private LocalDate depreciationDate;

    @NotNull(message = "Depreciation amount is required")
    @DecimalMin(value = "0.01", message = "Depreciation amount must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Depreciation amount must have at most 2 decimal places")
    private BigDecimal depreciationAmount;

    @NotNull(message = "Accumulated depreciation is required")
    @DecimalMin(value = "0.00", message = "Accumulated depreciation must be non-negative")
    @Digits(integer = 13, fraction = 2, message = "Accumulated depreciation must have at most 2 decimal places")
    private BigDecimal accumulatedDepreciation;
}