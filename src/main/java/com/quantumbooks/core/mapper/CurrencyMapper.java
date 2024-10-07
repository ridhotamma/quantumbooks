package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.CurrencyDto;
import com.quantumbooks.core.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toDto(Currency currency);

    Currency toEntity(CurrencyDto currencyDto);

    void updateEntityFromDto(CurrencyDto dto, @MappingTarget Currency entity);
}