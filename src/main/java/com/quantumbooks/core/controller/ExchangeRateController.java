package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.ExchangeRateDto;
import com.quantumbooks.core.service.ExchangeRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @PostMapping
    public ResponseEntity<ExchangeRateDto> createExchangeRate(@Valid @RequestBody ExchangeRateDto exchangeRateDto) {
        ExchangeRateDto createdExchangeRate = exchangeRateService.createExchangeRate(exchangeRateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExchangeRate);
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getExchangeRate(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        BigDecimal rate = exchangeRateService.getExchangeRate(fromCurrency, toCurrency, date);
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> getExchangeRateById(@PathVariable Integer id) {
        ExchangeRateDto exchangeRate = exchangeRateService.getExchangeRateById(id);
        return ResponseEntity.ok(exchangeRate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> updateExchangeRate(
            @PathVariable Integer id,
            @Valid @RequestBody ExchangeRateDto exchangeRateDto) {
        ExchangeRateDto updatedExchangeRate = exchangeRateService.updateExchangeRate(id, exchangeRateDto);
        return ResponseEntity.ok(updatedExchangeRate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchangeRate(@PathVariable Integer id) {
        exchangeRateService.deleteExchangeRate(id);
        return ResponseEntity.noContent().build();
    }
}