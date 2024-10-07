package com.quantumbooks.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}