package com.quantumbooks.core.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class BudgetDto {
    private Long budgetId;

    @NotBlank(message = "Budget name is required")
    @Size(max = 100, message = "Budget name must not exceed 100 characters")
    private String budgetName;

    @NotNull(message = "Fiscal year is required")
    @Min(value = 2000, message = "Fiscal year must be 2000 or later")
    @Max(value = 2100, message = "Fiscal year must be 2100 or earlier")
    private Integer fiscalYear;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }

    @Data
    public static class BudgetDetailDto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long budgetId;

        private List<BudgetItemDto> budgetItems;

        @Column(length = 100, nullable = false)
        private String budgetName;

        @Column(nullable = false)
        private Integer fiscalYear;

        @Column(nullable = false)
        private LocalDate startDate;

        @Column(nullable = false)
        private LocalDate endDate;
    }
}