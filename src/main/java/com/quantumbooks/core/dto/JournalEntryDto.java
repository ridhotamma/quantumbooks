package com.quantumbooks.core.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JournalEntryDto {
    private Long entryId;

    @NotNull(message = "Entry date is required")
    @PastOrPresent(message = "Entry date cannot be in the future")
    private LocalDate entryDate;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Posted by user ID is required")
    @Positive(message = "Posted by user ID must be a positive number")
    private Long postedBy;

    @NotNull(message = "Posted date is required")
    @PastOrPresent(message = "Posted date cannot be in the future")
    private LocalDateTime postedDate;
}