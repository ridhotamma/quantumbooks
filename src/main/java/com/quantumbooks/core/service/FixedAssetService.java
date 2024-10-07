package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.FixedAssetDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.FixedAsset;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.FixedAssetMapper;
import com.quantumbooks.core.repository.FixedAssetRepository;
import com.quantumbooks.core.specification.FixedAssetSpecification;
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
public class FixedAssetService {
    private final FixedAssetRepository fixedAssetRepository;
    private final FixedAssetMapper fixedAssetMapper;

    public PaginatedResponseDto<FixedAssetDto> getFixedAssets(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<FixedAsset> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(FixedAssetSpecification.searchFixedAssets(search));
        }

        Page<FixedAsset> fixedAssetPage = fixedAssetRepository.findAll(spec, pageable);

        List<FixedAssetDto> fixedAssets = fixedAssetPage.getContent().stream()
                .map(fixedAssetMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(fixedAssets, fixedAssetPage);
    }

    @Transactional
    public FixedAssetDto createFixedAsset(FixedAssetDto fixedAssetDto) {
        FixedAsset fixedAsset = fixedAssetMapper.toEntity(fixedAssetDto);
        FixedAsset savedFixedAsset = fixedAssetRepository.save(fixedAsset);
        return fixedAssetMapper.toDto(savedFixedAsset);
    }

    public FixedAssetDto getFixedAssetById(Long id) {
        FixedAsset fixedAsset = fixedAssetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset not found with id: " + id));
        return fixedAssetMapper.toDto(fixedAsset);
    }

    @Transactional
    public FixedAssetDto updateFixedAsset(Long id, FixedAssetDto fixedAssetDto) {
        FixedAsset existingFixedAsset = fixedAssetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset not found with id: " + id));

        FixedAsset updatedFixedAsset = fixedAssetMapper.toEntity(fixedAssetDto);
        updatedFixedAsset.setAssetId(existingFixedAsset.getAssetId());

        FixedAsset savedFixedAsset = fixedAssetRepository.save(updatedFixedAsset);
        return fixedAssetMapper.toDto(savedFixedAsset);
    }

    @Transactional
    public void deleteFixedAsset(Long id) {
        if (!fixedAssetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fixed Asset not found with id: " + id);
        }
        fixedAssetRepository.deleteById(id);
    }
}