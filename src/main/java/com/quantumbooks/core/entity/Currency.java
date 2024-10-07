package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer currencyId;

    @Column(length = 3, unique = true, nullable = false)
    private String currencyCode;

    @Column(length = 50, nullable = false)
    private String currencyName;
}