package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.AssetDepreciationDto;
import com.quantumbooks.core.entity.AssetDepreciation;
import com.quantumbooks.core.entity.FixedAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetDepreciationMapper {
    @Mapping(target = "assetId", source = "asset.assetId")
    AssetDepreciationDto toDto(AssetDepreciation entity);

    @Mapping(target = "asset", source = "assetId")
    AssetDepreciation toEntity(AssetDepreciationDto dto);

    default FixedAsset mapAssetId(Long assetId) {
        if (assetId == null) {
            return null;
        }
        FixedAsset asset = new FixedAsset();
        asset.setAssetId(assetId);
        return asset;
    }
}