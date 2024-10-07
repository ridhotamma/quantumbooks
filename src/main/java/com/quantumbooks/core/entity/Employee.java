package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salary;
}