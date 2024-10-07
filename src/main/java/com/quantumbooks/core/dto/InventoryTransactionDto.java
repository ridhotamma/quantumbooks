package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class InventoryTransactionDto {
    private Long transactionId;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    private Long referenceId;

    public enum TransactionType {
        PURCHASE, SALE, ADJUSTMENT, TRANSFER
    }
}