package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "BudgetItems")
public class BudgetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "BudgetID", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false)
    private Account account;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;
}