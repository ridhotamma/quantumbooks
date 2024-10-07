package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.CurrencyDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Currency;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.CurrencyMapper;
import com.quantumbooks.core.repository.CurrencyRepository;
import com.quantumbooks.core.specification.CurrencySpecification;
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
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public PaginatedResponseDto<CurrencyDto> getAllCurrencies(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Currency> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(CurrencySpecification.searchCurrencies(search));
        }

        Page<Currency> currencyPage = currencyRepository.findAll(spec, pageable);

        List<CurrencyDto> currencies = currencyPage.getContent().stream()
                .map(currencyMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(currencies, currencyPage);
    }

    public CurrencyDto getCurrencyById(Integer id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + id));
        return currencyMapper.toDto(currency);
    }

    @Transactional
    public CurrencyDto createCurrency(CurrencyDto currencyDto) {
        Currency currency = currencyMapper.toEntity(currencyDto);
        Currency savedCurrency = currencyRepository.save(currency);
        return currencyMapper.toDto(savedCurrency);
    }

    @Transactional
    public CurrencyDto updateCurrency(Integer id, CurrencyDto currencyDto) {
        Currency existingCurrency = currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + id));

        currencyMapper.updateEntityFromDto(currencyDto, existingCurrency);
        Currency updatedCurrency = currencyRepository.save(existingCurrency);
        return currencyMapper.toDto(updatedCurrency);
    }

    @Transactional
    public void deleteCurrency(Integer id) {
        if (!currencyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Currency not found with id: " + id);
        }
        currencyRepository.deleteById(id);
    }
}