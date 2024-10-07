package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.ExchangeRate;
import com.quantumbooks.core.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findTopByFromCurrencyAndToCurrencyAndEffectiveDateLessThanEqualOrderByEffectiveDateDesc(
            Currency fromCurrency, Currency toCurrency, LocalDate date);
}