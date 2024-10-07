package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TaxRates")
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taxRateId;

    @Column(length = 100)
    private String taxName;

    @Column(precision = 5, scale = 2)
    private BigDecimal rate;

    @Column
    private LocalDate effectiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxStatus status;

    public enum TaxStatus {
        ACTIVE, INACTIVE, EXPIRED
    }
}