package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BillItemDto {
    private Long itemId;

    @NotNull(message = "Bill ID is required")
    private Long billId;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Unit price must have at most 13 digits in total and 2 decimal places")
    private BigDecimal unitPrice;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.01", message = "Total price must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Total price must have at most 13 digits in total and 2 decimal places")
    private BigDecimal totalPrice;
}