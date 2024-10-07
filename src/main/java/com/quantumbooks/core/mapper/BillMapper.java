package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BillDto;
import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mappings({
            @Mapping(target = "vendorId", source = "vendor.vendorId")
    })
    BillDto toDto(Bill bill);

    @Mappings({
            @Mapping(target = "vendor", ignore = true)
    })
    Bill toEntity(BillDto billDto);

    default Bill updateEntityFromDto(BillDto billDto, Bill bill) {
        if (billDto == null) {
            return bill;
        }

        bill.setBillDate(billDto.getBillDate());
        bill.setDueDate(billDto.getDueDate());
        bill.setTotalAmount(billDto.getTotalAmount());
        bill.setPaidAmount(billDto.getPaidAmount());
        bill.setStatus(billDto.getStatus());

        return bill;
    }
}