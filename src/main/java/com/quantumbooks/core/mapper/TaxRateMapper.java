package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.TaxRateDto;
import com.quantumbooks.core.entity.TaxRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaxRateMapper {
    @Mapping(target = "status", expression = "java(mapStatus(taxRate.getStatus()))")
    TaxRateDto toDto(TaxRate taxRate);

    @Mapping(target = "status", expression = "java(mapStatus(taxRateDto.getStatus()))")
    TaxRate toEntity(TaxRateDto taxRateDto);

    default TaxRateDto.TaxStatus mapStatus(TaxRate.TaxStatus status) {
        return TaxRateDto.TaxStatus.valueOf(status.name());
    }

    default TaxRate.TaxStatus mapStatus(TaxRateDto.TaxStatus status) {
        return TaxRate.TaxStatus.valueOf(status.name());
    }
}