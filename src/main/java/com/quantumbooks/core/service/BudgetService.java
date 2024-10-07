package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BudgetDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Budget;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BudgetMapper;
import com.quantumbooks.core.repository.BudgetRepository;
import com.quantumbooks.core.specification.BudgetSpecification;
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
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public PaginatedResponseDto<BudgetDto> getBudgets(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Budget> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BudgetSpecification.searchBudgets(search));
        }

        Page<Budget> budgetPage = budgetRepository.findAll(spec, pageable);

        List<BudgetDto> budgets = budgetPage.getContent().stream()
                .map(budgetMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(budgets, budgetPage);
    }

    @Transactional
    public BudgetDto createBudget(BudgetDto budgetDto) {
        Budget budget = budgetMapper.toEntity(budgetDto);
        Budget savedBudget = budgetRepository.save(budget);
        return budgetMapper.toDto(savedBudget);
    }

    public BudgetDto getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        return budgetMapper.toDto(budget);
    }

    @Transactional
    public BudgetDto updateBudget(Long id, BudgetDto budgetDto) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));

        budgetMapper.updateEntityFromDto(budgetDto, existingBudget);
        Budget savedBudget = budgetRepository.save(existingBudget);
        return budgetMapper.toDto(savedBudget);
    }

    @Transactional
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }
}