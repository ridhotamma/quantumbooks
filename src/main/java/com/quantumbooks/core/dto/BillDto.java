package com.quantumbooks.core.dto;

import com.quantumbooks.core.entity.Bill.BillStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillDto {
    private Long billId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotNull(message = "Bill date is required")
    private LocalDate billDate;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Total amount must have at most 13 digits in total and 2 decimal places")
    private BigDecimal totalAmount;

    @DecimalMin(value = "0.00", message = "Paid amount must be 0 or greater")
    @Digits(integer = 13, fraction = 2, message = "Paid amount must have at most 13 digits in total and 2 decimal places")
    private BigDecimal paidAmount;

    @NotNull(message = "Status is required")
    private BillStatus status;
}