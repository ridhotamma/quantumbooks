package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BudgetDto;
import com.quantumbooks.core.dto.BudgetDto.BudgetDetailDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<BudgetDto>> getBudgets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "budgetId,asc") String[] sort) {
        PaginatedResponseDto<BudgetDto> response = budgetService.getBudgets(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BudgetDto> createBudget(@Valid @RequestBody BudgetDto budgetDto) {
        BudgetDto createdBudget = budgetService.createBudget(budgetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBudget);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDetailDto> getBudgetById(@PathVariable Long id) {
        BudgetDetailDto budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDto> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetDto budgetDto) {
        BudgetDto updatedBudget = budgetService.updateBudget(id, budgetDto);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}