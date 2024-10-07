package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Long accountId;

    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    private String accountName;

    @NotBlank(message = "Account type is required")
    @Size(min = 2, max = 50, message = "Account type must be between 2 and 50 characters")
    private String accountType;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Balance must be greater than or equal to 0.00")
    @Digits(integer = 13, fraction = 2, message = "Balance must have at most 13 digits in total with 2 decimal places")
    private BigDecimal balance;

    @Positive(message = "Parent account ID must be a positive number")
    private Long parentAccountId;
}