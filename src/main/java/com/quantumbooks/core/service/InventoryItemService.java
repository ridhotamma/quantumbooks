package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.InventoryItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.InventoryItem;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.InventoryItemMapper;
import com.quantumbooks.core.repository.InventoryItemRepository;
import com.quantumbooks.core.specification.InventoryItemSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemMapper inventoryItemMapper;

    public PaginatedResponseDto<InventoryItemDto> getInventoryItems(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<InventoryItem> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(InventoryItemSpecification.searchInventoryItems(search));
        }

        Page<InventoryItem> inventoryItemPage = inventoryItemRepository.findAll(spec, pageable);

        List<InventoryItemDto> inventoryItems = inventoryItemPage.getContent().stream()
                .map(inventoryItemMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(inventoryItems, inventoryItemPage);
    }

    @Transactional
    public InventoryItemDto createInventoryItem(InventoryItemDto inventoryItemDto) {
        InventoryItem inventoryItem = inventoryItemMapper.toEntity(inventoryItemDto);
        InventoryItem savedInventoryItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.toDto(savedInventoryItem);
    }

    public InventoryItemDto getInventoryItemById(Long id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found with id: " + id));
        return inventoryItemMapper.toDto(inventoryItem);
    }

    @Transactional
    public InventoryItemDto updateInventoryItem(Long id, InventoryItemDto inventoryItemDto) {
        InventoryItem existingInventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found with id: " + id));

        InventoryItem updatedInventoryItem = inventoryItemMapper.toEntity(inventoryItemDto);
        updatedInventoryItem.setItemId(existingInventoryItem.getItemId());

        InventoryItem savedInventoryItem = inventoryItemRepository.save(updatedInventoryItem);
        return inventoryItemMapper.toDto(savedInventoryItem);
    }

    @Transactional
    public void deleteInventoryItem(Long id) {
        if (!inventoryItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory Item not found with id: " + id);
        }
        inventoryItemRepository.deleteById(id);
    }
}