package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class InventoryItemDto {
    private Long itemId;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must not exceed 100 characters")
    private String itemName;

    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Unit price must have at most 2 decimal places")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity on hand is required")
    @Min(value = 0, message = "Quantity on hand must be non-negative")
    private Integer quantityOnHand;

    @NotNull(message = "Reorder point is required")
    @Min(value = 0, message = "Reorder point must be non-negative")
    private Integer reorderPoint;

    @NotNull(message = "Status is required")
    private ItemStatus status;

    public enum ItemStatus {
        IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    }
}