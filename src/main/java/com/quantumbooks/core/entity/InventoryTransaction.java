package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "InventoryTransactions")
public class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "ItemID", nullable = false)
    private InventoryItem item;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TransactionType transactionType;

    private Long referenceId;

    public enum TransactionType {
        PURCHASE, SALE, ADJUSTMENT, TRANSFER
    }
}