package com.quantumbooks.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class VendorDto {
    private Long vendorId;

    @NotBlank(message = "Vendor name is required")
    @Size(max = 100, message = "Vendor name must not exceed 100 characters")
    private String vendorName;

    @Size(max = 100, message = "Contact person name must not exceed 100 characters")
    private String contactPerson;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    private String address;
}