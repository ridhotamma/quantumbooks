package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.InvoiceDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Invoice.InvoiceStatus;
import com.quantumbooks.core.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponseDto<InvoiceDto>> getInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) InvoiceStatus status,
            @RequestParam(defaultValue = "invoiceID,asc") String[] sort) {
        PaginatedResponseDto<InvoiceDto> response = invoiceService.getInvoices(page, size, search, status, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        InvoiceDto invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto invoiceDto) {
        InvoiceDto updatedInvoice = invoiceService.updateInvoice(id, invoiceDto);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}