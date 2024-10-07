package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BankTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BankTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-transactions")
@RequiredArgsConstructor
public class BankTransactionController {
    private final BankTransactionService bankTransactionService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<BankTransactionDto>> getBankTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "transactionId,asc") String[] sort) {
        PaginatedResponseDto<BankTransactionDto> response = bankTransactionService.getBankTransactions(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BankTransactionDto> createBankTransaction(@Valid @RequestBody BankTransactionDto bankTransactionDto) {
        BankTransactionDto createdBankTransaction = bankTransactionService.createBankTransaction(bankTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTransactionDto> getBankTransactionById(@PathVariable Long id) {
        BankTransactionDto bankTransaction = bankTransactionService.getBankTransactionById(id);
        return ResponseEntity.ok(bankTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankTransactionDto> updateBankTransaction(@PathVariable Long id, @Valid @RequestBody BankTransactionDto bankTransactionDto) {
        BankTransactionDto updatedBankTransaction = bankTransactionService.updateBankTransaction(id, bankTransactionDto);
        return ResponseEntity.ok(updatedBankTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankTransaction(@PathVariable Long id) {
        bankTransactionService.deleteBankTransaction(id);
        return ResponseEntity.noContent().build();
    }
}