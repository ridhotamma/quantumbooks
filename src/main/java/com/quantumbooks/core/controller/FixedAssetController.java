package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.FixedAssetDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.FixedAssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fixed-assets")
@RequiredArgsConstructor
public class FixedAssetController {
    private final FixedAssetService fixedAssetService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<FixedAssetDto>> getFixedAssets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "assetId,asc") String[] sort) {
        PaginatedResponseDto<FixedAssetDto> response = fixedAssetService.getFixedAssets(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FixedAssetDto> createFixedAsset(@Valid @RequestBody FixedAssetDto fixedAssetDto) {
        FixedAssetDto createdFixedAsset = fixedAssetService.createFixedAsset(fixedAssetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFixedAsset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixedAssetDto> getFixedAssetById(@PathVariable Long id) {
        FixedAssetDto fixedAsset = fixedAssetService.getFixedAssetById(id);
        return ResponseEntity.ok(fixedAsset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixedAssetDto> updateFixedAsset(@PathVariable Long id, @Valid @RequestBody FixedAssetDto fixedAssetDto) {
        FixedAssetDto updatedFixedAsset = fixedAssetService.updateFixedAsset(id, fixedAssetDto);
        return ResponseEntity.ok(updatedFixedAsset);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFixedAsset(@PathVariable Long id) {
        fixedAssetService.deleteFixedAsset(id);
        return ResponseEntity.noContent().build();
    }
}