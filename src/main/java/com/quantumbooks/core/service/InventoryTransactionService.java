package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.InventoryTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.InventoryTransaction;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.InventoryTransactionMapper;
import com.quantumbooks.core.repository.InventoryTransactionRepository;
import com.quantumbooks.core.specification.InventoryTransactionSpecification;
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
public class InventoryTransactionService {
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public PaginatedResponseDto<InventoryTransactionDto> getInventoryTransactions(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<InventoryTransaction> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(InventoryTransactionSpecification.searchInventoryTransactions(search));
        }

        Page<InventoryTransaction> inventoryTransactionPage = inventoryTransactionRepository.findAll(spec, pageable);

        List<InventoryTransactionDto> inventoryTransactions = inventoryTransactionPage.getContent().stream()
                .map(inventoryTransactionMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(inventoryTransactions, inventoryTransactionPage);
    }

    @Transactional
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto inventoryTransactionDto) {
        InventoryTransaction inventoryTransaction = inventoryTransactionMapper.toEntity(inventoryTransactionDto);
        InventoryTransaction savedInventoryTransaction = inventoryTransactionRepository.save(inventoryTransaction);
        return inventoryTransactionMapper.toDto(savedInventoryTransaction);
    }

    public InventoryTransactionDto getInventoryTransactionById(Long id) {
        InventoryTransaction inventoryTransaction = inventoryTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Transaction not found with id: " + id));
        return inventoryTransactionMapper.toDto(inventoryTransaction);
    }

    @Transactional
    public InventoryTransactionDto updateInventoryTransaction(Long id, InventoryTransactionDto inventoryTransactionDto) {
        InventoryTransaction existingInventoryTransaction = inventoryTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Transaction not found with id: " + id));

        InventoryTransaction updatedInventoryTransaction = inventoryTransactionMapper.toEntity(inventoryTransactionDto);
        updatedInventoryTransaction.setTransactionId(existingInventoryTransaction.getTransactionId());

        InventoryTransaction savedInventoryTransaction = inventoryTransactionRepository.save(updatedInventoryTransaction);
        return inventoryTransactionMapper.toDto(savedInventoryTransaction);
    }

    @Transactional
    public void deleteInventoryTransaction(Long id) {
        if (!inventoryTransactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory Transaction not found with id: " + id);
        }
        inventoryTransactionRepository.deleteById(id);
    }
}