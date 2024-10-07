package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.TaxTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.TaxTransaction;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.TaxTransactionMapper;
import com.quantumbooks.core.repository.TaxTransactionRepository;
import com.quantumbooks.core.repository.TaxRateRepository;
import com.quantumbooks.core.specification.TaxTransactionSpecification;
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
public class TaxTransactionService {
    private final TaxTransactionRepository taxTransactionRepository;
    private final TaxRateRepository taxRateRepository;
    private final TaxTransactionMapper taxTransactionMapper;

    public PaginatedResponseDto<TaxTransactionDto> getTaxTransactions(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<TaxTransaction> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(TaxTransactionSpecification.searchTaxTransactions(search));
        }

        Page<TaxTransaction> taxTransactionPage = taxTransactionRepository.findAll(spec, pageable);

        List<TaxTransactionDto> taxTransactions = taxTransactionPage.getContent().stream()
                .map(taxTransactionMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(taxTransactions, taxTransactionPage);
    }

    @Transactional
    public TaxTransactionDto createTaxTransaction(TaxTransactionDto taxTransactionDto) {
        taxRateRepository.findById(taxTransactionDto.getTaxRateId())
                .orElseThrow(() -> new ResourceNotFoundException("Tax Rate not found with id: " + taxTransactionDto.getTaxRateId()));

        TaxTransaction taxTransaction = taxTransactionMapper.toEntity(taxTransactionDto);
        TaxTransaction savedTaxTransaction = taxTransactionRepository.save(taxTransaction);
        return taxTransactionMapper.toDto(savedTaxTransaction);
    }

    public TaxTransactionDto getTaxTransactionById(Long id) {
        TaxTransaction taxTransaction = taxTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Transaction not found with id: " + id));
        return taxTransactionMapper.toDto(taxTransaction);
    }

    @Transactional
    public TaxTransactionDto updateTaxTransaction(Long id, TaxTransactionDto taxTransactionDto) {
        TaxTransaction existingTaxTransaction = taxTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Transaction not found with id: " + id));

        taxRateRepository.findById(taxTransactionDto.getTaxRateId())
                .orElseThrow(() -> new ResourceNotFoundException("Tax Rate not found with id: " + taxTransactionDto.getTaxRateId()));

        TaxTransaction updatedTaxTransaction = taxTransactionMapper.toEntity(taxTransactionDto);
        updatedTaxTransaction.setTransactionId(existingTaxTransaction.getTransactionId());

        TaxTransaction savedTaxTransaction = taxTransactionRepository.save(updatedTaxTransaction);
        return taxTransactionMapper.toDto(savedTaxTransaction);
    }

    @Transactional
    public void deleteTaxTransaction(Long id) {
        if (!taxTransactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tax Transaction not found with id: " + id);
        }
        taxTransactionRepository.deleteById(id);
    }
}