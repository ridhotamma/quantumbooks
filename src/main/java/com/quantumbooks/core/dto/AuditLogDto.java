package com.quantumbooks.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogDto {
    private Long logId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Action date is required")
    private LocalDateTime actionDate;

    @NotBlank(message = "Action type is required")
    private String actionType;

    @NotBlank(message = "Table name is required")
    private String tableName;

    private Long recordId;

    private String oldValue;

    private String newValue;
}