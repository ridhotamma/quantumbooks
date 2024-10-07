package com.quantumbooks.core.dto;

import com.quantumbooks.core.entity.Customer.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {
    private Long customerID;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name must not exceed 100 characters")
    private String customerName;

    @Size(max = 100, message = "Contact person name must not exceed 100 characters")
    private String contactPerson;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    private String address;

    private CustomerStatus status;
}
