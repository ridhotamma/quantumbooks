package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Bills")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillID")
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "VendorID", nullable = false)
    private Vendor vendor;

    @Column(name = "BillDate", nullable = false)
    private LocalDate billDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDate dueDate;

    @Column(name = "TotalAmount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "PaidAmount", precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)
    private BillStatus status;

    public enum BillStatus {
        PENDING, PAID, OVERDUE, CANCELLED
    }
}