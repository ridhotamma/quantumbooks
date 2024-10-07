package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BillDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaginatedResponseDto<BillDto>> getBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "billId,asc") String[] sort) {
        PaginatedResponseDto<BillDto> response = billService.getBills(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillDto> createBill(@Valid @RequestBody BillDto billDto) {
        BillDto createdBill = billService.createBill(billDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BillDto> getBillById(@PathVariable Long id) {
        BillDto bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillDto> updateBill(@PathVariable Long id, @Valid @RequestBody BillDto billDto) {
        BillDto updatedBill = billService.updateBill(id, billDto);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}