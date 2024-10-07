package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.AssetDepreciationDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.AssetDepreciationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asset-depreciations")
@RequiredArgsConstructor
public class AssetDepreciationController {
    private final AssetDepreciationService assetDepreciationService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<AssetDepreciationDto>> getAssetDepreciations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "depreciationId,asc") String[] sort) {
        PaginatedResponseDto<AssetDepreciationDto> response = assetDepreciationService.getAssetDepreciations(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AssetDepreciationDto> createAssetDepreciation(@Valid @RequestBody AssetDepreciationDto assetDepreciationDto) {
        AssetDepreciationDto createdAssetDepreciation = assetDepreciationService.createAssetDepreciation(assetDepreciationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssetDepreciation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDepreciationDto> getAssetDepreciationById(@PathVariable Long id) {
        AssetDepreciationDto assetDepreciation = assetDepreciationService.getAssetDepreciationById(id);
        return ResponseEntity.ok(assetDepreciation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDepreciationDto> updateAssetDepreciation(@PathVariable Long id, @Valid @RequestBody AssetDepreciationDto assetDepreciationDto) {
        AssetDepreciationDto updatedAssetDepreciation = assetDepreciationService.updateAssetDepreciation(id, assetDepreciationDto);
        return ResponseEntity.ok(updatedAssetDepreciation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetDepreciation(@PathVariable Long id) {
        assetDepreciationService.deleteAssetDepreciation(id);
        return ResponseEntity.noContent().build();
    }
}