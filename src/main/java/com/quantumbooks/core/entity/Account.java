package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, length = 100)
    private String accountName;

    @Column(nullable = false, length = 50)
    private String accountType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "ParentAccountID")
    private Account parentAccount;
}