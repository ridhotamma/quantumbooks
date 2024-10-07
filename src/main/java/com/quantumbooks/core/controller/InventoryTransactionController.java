package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.InventoryTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.InventoryTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {
    private final InventoryTransactionService inventoryTransactionService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<InventoryTransactionDto>> getInventoryTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "transactionId,asc") String[] sort) {
        PaginatedResponseDto<InventoryTransactionDto> response = inventoryTransactionService.getInventoryTransactions(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<InventoryTransactionDto> createInventoryTransaction(@Valid @RequestBody InventoryTransactionDto inventoryTransactionDto) {
        InventoryTransactionDto createdInventoryTransaction = inventoryTransactionService.createInventoryTransaction(inventoryTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventoryTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryTransactionDto> getInventoryTransactionById(@PathVariable Long id) {
        InventoryTransactionDto inventoryTransaction = inventoryTransactionService.getInventoryTransactionById(id);
        return ResponseEntity.ok(inventoryTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryTransactionDto> updateInventoryTransaction(@PathVariable Long id, @Valid @RequestBody InventoryTransactionDto inventoryTransactionDto) {
        InventoryTransactionDto updatedInventoryTransaction = inventoryTransactionService.updateInventoryTransaction(id, inventoryTransactionDto);
        return ResponseEntity.ok(updatedInventoryTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteInventoryTransaction(id);
        return ResponseEntity.noContent().build();
    }
}