package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.CurrencyDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<CurrencyDto>> getAllCurrencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "currencyId,asc") String[] sort) {
        PaginatedResponseDto<CurrencyDto> currencies = currencyService.getAllCurrencies(page, size, search, sort);
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDto> getCurrencyById(@PathVariable Integer id) {
        CurrencyDto currency = currencyService.getCurrencyById(id);
        return ResponseEntity.ok(currency);
    }

    @PostMapping
    public ResponseEntity<CurrencyDto> createCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        CurrencyDto createdCurrency = currencyService.createCurrency(currencyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCurrency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDto> updateCurrency(
            @PathVariable Integer id,
            @Valid @RequestBody CurrencyDto currencyDto) {
        CurrencyDto updatedCurrency = currencyService.updateCurrency(id, currencyDto);
        return ResponseEntity.ok(updatedCurrency);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Integer id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }
}