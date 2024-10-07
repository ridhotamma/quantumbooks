package com.quantumbooks.core.dto;

import com.quantumbooks.core.entity.Customer;
import com.quantumbooks.core.entity.Invoice.InvoiceStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDto {
    private Long invoiceID;

    private Customer customer;

    @NotNull(message = "Customer ID is required")
    private Long customerID;

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero")
    private BigDecimal totalAmount;

    @NotNull(message = "Paid amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Paid amount must be zero or greater")
    private BigDecimal paidAmount;

    @NotNull(message = "Status is required")
    private InvoiceStatus status;
}