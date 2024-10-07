package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FixedAssetDto {
    private Long assetId;

    @NotBlank(message = "Asset name is required")
    @Size(max = 100, message = "Asset name must not exceed 100 characters")
    private String assetName;

    private String description;

    @NotNull(message = "Purchase date is required")
    @PastOrPresent(message = "Purchase date must be in the past or present")
    private LocalDate purchaseDate;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Purchase price must have at most 2 decimal places")
    private BigDecimal purchasePrice;

    @Size(max = 50, message = "Depreciation method must not exceed 50 characters")
    private String depreciationMethod;

    @Min(value = 1, message = "Useful life must be at least 1 year")
    private Integer usefulLife;

    @DecimalMin(value = "0.00", message = "Salvage value must be non-negative")
    @Digits(integer = 13, fraction = 2, message = "Salvage value must have at most 2 decimal places")
    private BigDecimal salvageValue;

    @NotNull(message = "Asset status is required")
    private AssetStatus status;

    public enum AssetStatus {
        ACTIVE, DISPOSED, FULLY_DEPRECIATED
    }
}