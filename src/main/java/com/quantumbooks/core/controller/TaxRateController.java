package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.TaxRateDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.TaxRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax-rates")
@RequiredArgsConstructor
public class TaxRateController {
    private final TaxRateService taxRateService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<TaxRateDto>> getTaxRates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "taxRateId,asc") String[] sort) {
        PaginatedResponseDto<TaxRateDto> response = taxRateService.getTaxRates(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaxRateDto> createTaxRate(@Valid @RequestBody TaxRateDto taxRateDto) {
        TaxRateDto createdTaxRate = taxRateService.createTaxRate(taxRateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaxRate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxRateDto> getTaxRateById(@PathVariable Long id) {
        TaxRateDto taxRate = taxRateService.getTaxRateById(id);
        return ResponseEntity.ok(taxRate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxRateDto> updateTaxRate(@PathVariable Long id, @Valid @RequestBody TaxRateDto taxRateDto) {
        TaxRateDto updatedTaxRate = taxRateService.updateTaxRate(id, taxRateDto);
        return ResponseEntity.ok(updatedTaxRate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxRate(@PathVariable Long id) {
        taxRateService.deleteTaxRate(id);
        return ResponseEntity.noContent().build();
    }
}