package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PayrollTransactionDto {
    private Long transactionId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Pay period start date is required")
    private LocalDate payPeriodStart;

    @NotNull(message = "Pay period end date is required")
    private LocalDate payPeriodEnd;

    @NotNull(message = "Gross pay is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gross pay must be greater than zero")
    @Digits(integer = 13, fraction = 2, message = "Gross pay must have at most 13 digits in total and 2 decimal places")
    private BigDecimal grossPay;

    @NotNull(message = "Deductions are required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Deductions must be zero or greater")
    @Digits(integer = 13, fraction = 2, message = "Deductions must have at most 13 digits in total and 2 decimal places")
    private BigDecimal deductions;

    @NotNull(message = "Net pay is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Net pay must be greater than zero")
    @Digits(integer = 13, fraction = 2, message = "Net pay must have at most 13 digits in total and 2 decimal places")
    private BigDecimal netPay;
}