package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.FixedAssetDto;
import com.quantumbooks.core.entity.FixedAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FixedAssetMapper {
    FixedAssetDto toDto(FixedAsset entity);

    @Mapping(target = "assetId", ignore = true)
    FixedAsset toEntity(FixedAssetDto dto);
}