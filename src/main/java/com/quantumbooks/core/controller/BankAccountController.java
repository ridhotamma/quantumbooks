package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BankAccountDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<BankAccountDto>> getBankAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "bankAccountId,asc") String[] sort) {
        PaginatedResponseDto<BankAccountDto> response = bankAccountService.getBankAccounts(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BankAccountDto> createBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto) {
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(bankAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getBankAccountById(@PathVariable Long id) {
        BankAccountDto bankAccount = bankAccountService.getBankAccountById(id);
        return ResponseEntity.ok(bankAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDto> updateBankAccount(@PathVariable Long id, @Valid @RequestBody BankAccountDto bankAccountDto) {
        BankAccountDto updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccountDto);
        return ResponseEntity.ok(updatedBankAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}