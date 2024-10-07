package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "BillItems")
@Data
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemID")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "BillID", nullable = false)
    private Bill bill;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "UnitPrice", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "TotalPrice", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;
}