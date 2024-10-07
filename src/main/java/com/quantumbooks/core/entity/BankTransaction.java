package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "BankTransactions")
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "BankAccountID", nullable = false)
    private BankAccount bankAccount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Boolean reconciled;

    public enum TransactionType {
        DEBIT, CREDIT
    }
}