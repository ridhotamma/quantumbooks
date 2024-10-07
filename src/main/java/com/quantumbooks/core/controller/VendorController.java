package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.VendorDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<PaginatedResponseDto<VendorDto>> getVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "vendorId,asc") String[] sort) {

        PaginatedResponseDto<VendorDto> response = vendorService.getVendors(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<VendorDto> createVendor(@Valid @RequestBody VendorDto vendorDto) {
        VendorDto createdVendor = vendorService.createVendor(vendorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<VendorDto> getVendorById(@PathVariable Long id) {
        VendorDto vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<VendorDto> updateVendor(@PathVariable Long id, @Valid @RequestBody VendorDto vendorDto) {
        VendorDto updatedVendor = vendorService.updateVendor(id, vendorDto);
        return ResponseEntity.ok(updatedVendor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
}