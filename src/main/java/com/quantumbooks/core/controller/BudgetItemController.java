package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BudgetItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BudgetItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/budget-items")
@RequiredArgsConstructor
public class BudgetItemController {
    private final BudgetItemService budgetItemService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<BudgetItemDto>> getBudgetItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long budgetId,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(defaultValue = "itemId,asc") String[] sort) {
        PaginatedResponseDto<BudgetItemDto> response = budgetItemService.getBudgetItems(page, size, search, budgetId, accountId, minAmount, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BudgetItemDto> createBudgetItem(@Valid @RequestBody BudgetItemDto budgetItemDto) {
        BudgetItemDto createdBudgetItem = budgetItemService.createBudgetItem(budgetItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBudgetItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetItemDto> getBudgetItemById(@PathVariable Long id) {
        BudgetItemDto budgetItem = budgetItemService.getBudgetItemById(id);
        return ResponseEntity.ok(budgetItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetItemDto> updateBudgetItem(@PathVariable Long id, @Valid @RequestBody BudgetItemDto budgetItemDto) {
        BudgetItemDto updatedBudgetItem = budgetItemService.updateBudgetItem(id, budgetItemDto);
        return ResponseEntity.ok(updatedBudgetItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetItem(@PathVariable Long id) {
        budgetItemService.deleteBudgetItem(id);
        return ResponseEntity.noContent().build();
    }
}