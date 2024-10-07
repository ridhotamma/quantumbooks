package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.ExchangeRateDto;
import com.quantumbooks.core.entity.Currency;
import com.quantumbooks.core.entity.ExchangeRate;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.ExchangeRateMapper;
import com.quantumbooks.core.repository.CurrencyRepository;
import com.quantumbooks.core.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Transactional
    public ExchangeRateDto createExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = exchangeRateMapper.toEntity(exchangeRateDto);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRate);
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    public BigDecimal getExchangeRate(String fromCurrencyCode, String toCurrencyCode, LocalDate date) {
        Currency fromCurrency = currencyRepository.findByCurrencyCode(fromCurrencyCode)
                .orElseThrow(() -> new ResourceNotFoundException("From currency not found: " + fromCurrencyCode));
        Currency toCurrency = currencyRepository.findByCurrencyCode(toCurrencyCode)
                .orElseThrow(() -> new ResourceNotFoundException("To currency not found: " + toCurrencyCode));

        ExchangeRate exchangeRate = exchangeRateRepository
                .findTopByFromCurrencyAndToCurrencyAndEffectiveDateLessThanEqualOrderByEffectiveDateDesc(fromCurrency, toCurrency, date)
                .orElseThrow(() -> new ResourceNotFoundException("Exchange rate not found for the given currencies and date"));

        return exchangeRate.getRate();
    }

    public ExchangeRateDto getExchangeRateById(Integer id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exchange rate not found with id: " + id));
        return exchangeRateMapper.toDto(exchangeRate);
    }

    @Transactional
    public ExchangeRateDto updateExchangeRate(Integer id, ExchangeRateDto exchangeRateDto) {
        ExchangeRate existingExchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exchange rate not found with id: " + id));

        exchangeRateMapper.updateEntityFromDto(exchangeRateDto, existingExchangeRate);
        ExchangeRate updatedExchangeRate = exchangeRateRepository.save(existingExchangeRate);
        return exchangeRateMapper.toDto(updatedExchangeRate);
    }

    @Transactional
    public void deleteExchangeRate(Integer id) {
        if (!exchangeRateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exchange rate not found with id: " + id);
        }
        exchangeRateRepository.deleteById(id);
    }
}