package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.InventoryItemDto;
import com.quantumbooks.core.entity.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {
    InventoryItemDto toDto(InventoryItem entity);

    @Mapping(target = "itemId", ignore = true)
    InventoryItem toEntity(InventoryItemDto dto);
}
