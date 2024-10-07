package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "BankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountId;

    @Column(length = 100)
    private String accountName;

    @Column(length = 50)
    private String accountNumber;

    @Column(length = 100)
    private String bankName;

    @Column(precision = 15, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    public enum AccountStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
}