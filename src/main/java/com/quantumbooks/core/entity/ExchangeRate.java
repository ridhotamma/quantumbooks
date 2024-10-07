package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ExchangeRates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeRateId;

    @ManyToOne
    @JoinColumn(name = "FromCurrencyID", nullable = false)
    private Currency fromCurrency;

    @ManyToOne
    @JoinColumn(name = "ToCurrencyID", nullable = false)
    private Currency toCurrency;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal rate;

    @Column(nullable = false)
    private LocalDate effectiveDate;
}