package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.BillItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.BillItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bill-items")
@RequiredArgsConstructor
public class BillItemController {
    private final BillItemService billItemService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaginatedResponseDto<BillItemDto>> getBillItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "itemId,asc") String[] sort) {
        PaginatedResponseDto<BillItemDto> response = billItemService.getBillItems(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillItemDto> createBillItem(@Valid @RequestBody BillItemDto billItemDto) {
        BillItemDto createdBillItem = billItemService.createBillItem(billItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBillItem);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BillItemDto> getBillItemById(@PathVariable Long id) {
        BillItemDto billItem = billItemService.getBillItemById(id);
        return ResponseEntity.ok(billItem);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillItemDto> updateBillItem(@PathVariable Long id, @Valid @RequestBody BillItemDto billItemDto) {
        BillItemDto updatedBillItem = billItemService.updateBillItem(id, billItemDto);
        return ResponseEntity.ok(updatedBillItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBillItem(@PathVariable Long id) {
        billItemService.deleteBillItem(id);
        return ResponseEntity.noContent().build();
    }
}