package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "FixedAssets")
public class FixedAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @Column(length = 100, nullable = false)
    private String assetName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal purchasePrice;

    @Column(length = 50)
    private String depreciationMethod;

    private Integer usefulLife;

    @Column(precision = 15, scale = 2)
    private BigDecimal salvageValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    public enum AssetStatus {
        ACTIVE, DISPOSED, FULLY_DEPRECIATED
    }
}