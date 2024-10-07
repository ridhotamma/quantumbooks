package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.VendorDto;
import com.quantumbooks.core.entity.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorDto toDto(Vendor vendor);
    Vendor toEntity(VendorDto vendorDto);
}