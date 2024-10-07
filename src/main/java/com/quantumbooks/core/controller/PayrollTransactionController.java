package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.PayrollTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.PayrollTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payroll-transactions")
@RequiredArgsConstructor
public class PayrollTransactionController {
    private final PayrollTransactionService payrollTransactionService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<PayrollTransactionDto>> getPayrollTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "transactionId,asc") String[] sort) {
        PaginatedResponseDto<PayrollTransactionDto> response = payrollTransactionService.getPayrollTransactions(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PayrollTransactionDto> createPayrollTransaction(@Valid @RequestBody PayrollTransactionDto payrollTransactionDto) {
        PayrollTransactionDto createdPayrollTransaction = payrollTransactionService.createPayrollTransaction(payrollTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayrollTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollTransactionDto> getPayrollTransactionById(@PathVariable Long id) {
        PayrollTransactionDto payrollTransaction = payrollTransactionService.getPayrollTransactionById(id);
        return ResponseEntity.ok(payrollTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollTransactionDto> updatePayrollTransaction(@PathVariable Long id, @Valid @RequestBody PayrollTransactionDto payrollTransactionDto) {
        PayrollTransactionDto updatedPayrollTransaction = payrollTransactionService.updatePayrollTransaction(id, payrollTransactionDto);
        return ResponseEntity.ok(updatedPayrollTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayrollTransaction(@PathVariable Long id) {
        payrollTransactionService.deletePayrollTransaction(id);
        return ResponseEntity.noContent().build();
    }
}