package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.InventoryTransactionDto;
import com.quantumbooks.core.entity.InventoryTransaction;
import com.quantumbooks.core.entity.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {
    @Mapping(target = "itemId", source = "item.itemId")
    InventoryTransactionDto toDto(InventoryTransaction entity);

    @Mapping(target = "item", source = "itemId")
    InventoryTransaction toEntity(InventoryTransactionDto dto);

    default InventoryItem mapItemId(Long itemId) {
        if (itemId == null) {
            return null;
        }
        InventoryItem item = new InventoryItem();
        item.setItemId(itemId);
        return item;
    }
}