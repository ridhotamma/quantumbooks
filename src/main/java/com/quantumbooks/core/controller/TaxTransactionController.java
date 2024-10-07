package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.TaxTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.TaxTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax-transactions")
@RequiredArgsConstructor
public class TaxTransactionController {
    private final TaxTransactionService taxTransactionService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<TaxTransactionDto>> getTaxTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "transactionId,asc") String[] sort) {
        PaginatedResponseDto<TaxTransactionDto> response = taxTransactionService.getTaxTransactions(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaxTransactionDto> createTaxTransaction(@Valid @RequestBody TaxTransactionDto taxTransactionDto) {
        TaxTransactionDto createdTaxTransaction = taxTransactionService.createTaxTransaction(taxTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaxTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxTransactionDto> getTaxTransactionById(@PathVariable Long id) {
        TaxTransactionDto taxTransaction = taxTransactionService.getTaxTransactionById(id);
        return ResponseEntity.ok(taxTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxTransactionDto> updateTaxTransaction(@PathVariable Long id, @Valid @RequestBody TaxTransactionDto taxTransactionDto) {
        TaxTransactionDto updatedTaxTransaction = taxTransactionService.updateTaxTransaction(id, taxTransactionDto);
        return ResponseEntity.ok(updatedTaxTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxTransaction(@PathVariable Long id) {
        taxTransactionService.deleteTaxTransaction(id);
        return ResponseEntity.noContent().build();
    }
}