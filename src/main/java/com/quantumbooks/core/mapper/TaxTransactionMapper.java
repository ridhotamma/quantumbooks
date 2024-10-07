package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.TaxTransactionDto;
import com.quantumbooks.core.entity.TaxTransaction;
import com.quantumbooks.core.entity.TaxRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaxTransactionMapper {
    @Mapping(target = "taxRateId", source = "taxRate.taxRateId")
    @Mapping(target = "status", expression = "java(mapStatus(taxTransaction.getStatus()))")
    TaxTransactionDto toDto(TaxTransaction taxTransaction);

    @Mapping(target = "taxRate", expression = "java(mapTaxRate(taxTransactionDto))")
    @Mapping(target = "status", expression = "java(mapStatus(taxTransactionDto.getStatus()))")
    TaxTransaction toEntity(TaxTransactionDto taxTransactionDto);

    default TaxTransactionDto.TransactionStatus mapStatus(TaxTransaction.TransactionStatus status) {
        return TaxTransactionDto.TransactionStatus.valueOf(status.name());
    }

    default TaxTransaction.TransactionStatus mapStatus(TaxTransactionDto.TransactionStatus status) {
        return TaxTransaction.TransactionStatus.valueOf(status.name());
    }

    default TaxRate mapTaxRate(TaxTransactionDto dto) {
        if (dto.getTaxRateId() == null) {
            return null;
        }
        TaxRate taxRate = new TaxRate();
        taxRate.setTaxRateId(dto.getTaxRateId());
        return taxRate;
    }
}