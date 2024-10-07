package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.InventoryItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory-items")
@RequiredArgsConstructor
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<InventoryItemDto>> getInventoryItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "itemId,asc") String[] sort) {
        PaginatedResponseDto<InventoryItemDto> response = inventoryItemService.getInventoryItems(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<InventoryItemDto> createInventoryItem(@Valid @RequestBody InventoryItemDto inventoryItemDto) {
        InventoryItemDto createdInventoryItem = inventoryItemService.createInventoryItem(inventoryItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventoryItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemDto> getInventoryItemById(@PathVariable Long id) {
        InventoryItemDto inventoryItem = inventoryItemService.getInventoryItemById(id);
        return ResponseEntity.ok(inventoryItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemDto> updateInventoryItem(@PathVariable Long id, @Valid @RequestBody InventoryItemDto inventoryItemDto) {
        InventoryItemDto updatedInventoryItem = inventoryItemService.updateInventoryItem(id, inventoryItemDto);
        return ResponseEntity.ok(updatedInventoryItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }
}