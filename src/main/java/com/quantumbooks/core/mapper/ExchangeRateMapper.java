package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.ExchangeRateDto;
import com.quantumbooks.core.entity.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    @Mapping(target = "fromCurrencyId", source = "fromCurrency.currencyId")
    @Mapping(target = "toCurrencyId", source = "toCurrency.currencyId")
    ExchangeRateDto toDto(ExchangeRate exchangeRate);

    @Mapping(target = "fromCurrency.currencyId", source = "fromCurrencyId")
    @Mapping(target = "toCurrency.currencyId", source = "toCurrencyId")
    ExchangeRate toEntity(ExchangeRateDto exchangeRateDto);

    @Mapping(target = "fromCurrency.currencyId", source = "fromCurrencyId")
    @Mapping(target = "toCurrency.currencyId", source = "toCurrencyId")
    void updateEntityFromDto(ExchangeRateDto dto, @MappingTarget ExchangeRate entity);
}