package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.AssetDepreciationDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.AssetDepreciation;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.AssetDepreciationMapper;
import com.quantumbooks.core.repository.AssetDepreciationRepository;
import com.quantumbooks.core.specification.AssetDepreciationSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetDepreciationService {
    private final AssetDepreciationRepository assetDepreciationRepository;
    private final AssetDepreciationMapper assetDepreciationMapper;

    public PaginatedResponseDto<AssetDepreciationDto> getAssetDepreciations(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<AssetDepreciation> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(AssetDepreciationSpecification.searchAssetDepreciations(search));
        }

        Page<AssetDepreciation> assetDepreciationPage = assetDepreciationRepository.findAll(spec, pageable);

        List<AssetDepreciationDto> assetDepreciations = assetDepreciationPage.getContent().stream()
                .map(assetDepreciationMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(assetDepreciations, assetDepreciationPage);
    }

    @Transactional
    public AssetDepreciationDto createAssetDepreciation(AssetDepreciationDto assetDepreciationDto) {
        AssetDepreciation assetDepreciation = assetDepreciationMapper.toEntity(assetDepreciationDto);
        AssetDepreciation savedAssetDepreciation = assetDepreciationRepository.save(assetDepreciation);
        return assetDepreciationMapper.toDto(savedAssetDepreciation);
    }

    public AssetDepreciationDto getAssetDepreciationById(Long id) {
        AssetDepreciation assetDepreciation = assetDepreciationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Depreciation not found with id: " + id));
        return assetDepreciationMapper.toDto(assetDepreciation);
    }

    @Transactional
    public AssetDepreciationDto updateAssetDepreciation(Long id, AssetDepreciationDto assetDepreciationDto) {
        AssetDepreciation existingAssetDepreciation = assetDepreciationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Depreciation not found with id: " + id));

        AssetDepreciation updatedAssetDepreciation = assetDepreciationMapper.toEntity(assetDepreciationDto);
        updatedAssetDepreciation.setDepreciationId(existingAssetDepreciation.getDepreciationId());

        AssetDepreciation savedAssetDepreciation = assetDepreciationRepository.save(updatedAssetDepreciation);
        return assetDepreciationMapper.toDto(savedAssetDepreciation);
    }

    @Transactional
    public void deleteAssetDepreciation(Long id) {
        if (!assetDepreciationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset Depreciation not found with id: " + id);
        }
        assetDepreciationRepository.deleteById(id);
    }
}