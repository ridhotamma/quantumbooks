package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BudgetItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.BudgetItem;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BudgetItemMapper;
import com.quantumbooks.core.repository.BudgetItemRepository;
import com.quantumbooks.core.specification.BudgetItemSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetItemService {
    private final BudgetItemRepository budgetItemRepository;
    private final BudgetItemMapper budgetItemMapper;

    public PaginatedResponseDto<BudgetItemDto> getBudgetItems(int page, int size, String search, Long budgetId, Long accountId, BigDecimal minAmount, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<BudgetItem> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BudgetItemSpecification.searchBudgetItems(search));
        }
        if (budgetId != null) {
            spec = spec.and(BudgetItemSpecification.hasBudgetId(budgetId));
        }
        if (accountId != null) {
            spec = spec.and(BudgetItemSpecification.hasAccountId(accountId));
        }
        if (minAmount != null) {
            spec = spec.and(BudgetItemSpecification.hasAmountGreaterThan(minAmount));
        }

        Page<BudgetItem> budgetItemPage = budgetItemRepository.findAll(spec, pageable);

        List<BudgetItemDto> budgetItems = budgetItemPage.getContent().stream()
                .map(budgetItemMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(budgetItems, budgetItemPage);
    }

    @Transactional
    public BudgetItemDto createBudgetItem(BudgetItemDto budgetItemDto) {
        BudgetItem budgetItem = budgetItemMapper.toEntity(budgetItemDto);
        BudgetItem savedBudgetItem = budgetItemRepository.save(budgetItem);
        return budgetItemMapper.toDto(savedBudgetItem);
    }

    public BudgetItemDto getBudgetItemById(Long id) {
        BudgetItem budgetItem = budgetItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget Item not found with id: " + id));
        return budgetItemMapper.toDto(budgetItem);
    }

    @Transactional
    public BudgetItemDto updateBudgetItem(Long id, BudgetItemDto budgetItemDto) {
        BudgetItem existingBudgetItem = budgetItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget Item not found with id: " + id));

        budgetItemMapper.updateEntityFromDto(budgetItemDto, existingBudgetItem);
        BudgetItem savedBudgetItem = budgetItemRepository.save(existingBudgetItem);
        return budgetItemMapper.toDto(savedBudgetItem);
    }

    @Transactional
    public void deleteBudgetItem(Long id) {
        if (!budgetItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget Item not found with id: " + id);
        }
        budgetItemRepository.deleteById(id);
    }
}