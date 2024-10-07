package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TaxTransactions")
public class TaxTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "taxRateId", nullable = false)
    private TaxRate taxRate;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal taxableAmount;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING, COMPLETED, CANCELLED
    }
}