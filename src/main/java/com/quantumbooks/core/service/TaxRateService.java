package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.TaxRateDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.TaxRate;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.TaxRateMapper;
import com.quantumbooks.core.repository.TaxRateRepository;
import com.quantumbooks.core.specification.TaxRateSpecification;
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
public class TaxRateService {
    private final TaxRateRepository taxRateRepository;
    private final TaxRateMapper taxRateMapper;

    public PaginatedResponseDto<TaxRateDto> getTaxRates(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<TaxRate> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(TaxRateSpecification.searchTaxRates(search));
        }

        Page<TaxRate> taxRatePage = taxRateRepository.findAll(spec, pageable);

        List<TaxRateDto> taxRates = taxRatePage.getContent().stream()
                .map(taxRateMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(taxRates, taxRatePage);
    }

    @Transactional
    public TaxRateDto createTaxRate(TaxRateDto taxRateDto) {
        TaxRate taxRate = taxRateMapper.toEntity(taxRateDto);
        TaxRate savedTaxRate = taxRateRepository.save(taxRate);
        return taxRateMapper.toDto(savedTaxRate);
    }

    public TaxRateDto getTaxRateById(Long id) {
        TaxRate taxRate = taxRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Rate not found with id: " + id));
        return taxRateMapper.toDto(taxRate);
    }

    @Transactional
    public TaxRateDto updateTaxRate(Long id, TaxRateDto taxRateDto) {
        TaxRate existingTaxRate = taxRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Rate not found with id: " + id));

        TaxRate updatedTaxRate = taxRateMapper.toEntity(taxRateDto);
        updatedTaxRate.setTaxRateId(existingTaxRate.getTaxRateId());

        TaxRate savedTaxRate = taxRateRepository.save(updatedTaxRate);
        return taxRateMapper.toDto(savedTaxRate);
    }

    @Transactional
    public void deleteTaxRate(Long id) {
        if (!taxRateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tax Rate not found with id: " + id);
        }
        taxRateRepository.deleteById(id);
    }
}