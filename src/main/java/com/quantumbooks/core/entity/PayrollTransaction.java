package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "PayrollTransactions")
public class PayrollTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate payPeriodStart;

    @Column(nullable = false)
    private LocalDate payPeriodEnd;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal grossPay;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal deductions;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal netPay;
}