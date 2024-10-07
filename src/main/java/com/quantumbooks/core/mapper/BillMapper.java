package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BillDto;
import com.quantumbooks.core.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(target = "vendorId", source = "vendor.vendorId")
    BillDto toDto(Bill bill);

    @Mapping(target = "vendor", ignore = true)
    Bill toEntity(BillDto billDto);
}
