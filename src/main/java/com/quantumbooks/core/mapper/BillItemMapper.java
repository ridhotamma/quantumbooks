package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BillItemDto;
import com.quantumbooks.core.entity.BillItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BillItemMapper {
    @Mappings({
            @Mapping(target = "billId", source = "bill.billId")
    })
    BillItemDto toDto(BillItem billItem);

    @Mappings({
            @Mapping(target = "bill", ignore = true)
    })
    BillItem toEntity(BillItemDto billItemDto);

    default void updateEntityFromDto(BillItemDto billItemDto, BillItem billItem) {
        if (billItemDto == null) {
            return;
        }

        billItem.setDescription(billItemDto.getDescription());
        billItem.setQuantity(billItemDto.getQuantity());
        billItem.setUnitPrice(billItemDto.getUnitPrice());
        billItem.setTotalPrice(billItemDto.getTotalPrice());

    }
}