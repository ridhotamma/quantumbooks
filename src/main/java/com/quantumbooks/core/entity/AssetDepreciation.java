package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "AssetDepreciation")
public class AssetDepreciation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depreciationId;

    @ManyToOne
    @JoinColumn(name = "AssetID", nullable = false)
    private FixedAsset asset;

    @Column(nullable = false)
    private LocalDate depreciationDate;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal depreciationAmount;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal accumulatedDepreciation;
}