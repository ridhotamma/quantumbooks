package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BillItemDto;
import com.quantumbooks.core.entity.BillItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillItemMapper {
    @Mapping(target = "billId", source = "bill.billId")
    BillItemDto toDto(BillItem billItem);

    @Mapping(target = "bill", ignore = true)
    BillItem toEntity(BillItemDto billItemDto);
}