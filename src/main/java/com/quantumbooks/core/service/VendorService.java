package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.VendorDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Vendor;
import com.quantumbooks.core.mapper.VendorMapper;
import com.quantumbooks.core.repository.VendorRepository;
import com.quantumbooks.core.specification.VendorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<VendorDto> getVendors(int page, int size, String search, String[] sort) {
        Specification<Vendor> spec = VendorSpecification.searchByVendorName(search);
        Pageable pageable = createPageable(page, size, sort);

        Page<Vendor> vendorPage = vendorRepository.findAll(spec, pageable);

        List<VendorDto> vendorDtos = vendorPage.getContent().stream()
                .map(vendorMapper::toDto)
                .collect(Collectors.toList());

        return createPaginatedResponse(vendorDtos, vendorPage);
    }

    @Transactional
    public VendorDto createVendor(VendorDto vendorDto) {
        Vendor vendor = vendorMapper.toEntity(vendorDto);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.toDto(savedVendor);
    }

    @Transactional(readOnly = true)
    public VendorDto getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return vendorMapper.toDto(vendor);
    }

    @Transactional
    public VendorDto updateVendor(Long id, VendorDto vendorDto) {
        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Vendor updatedVendor = vendorMapper.toEntity(vendorDto);
        updatedVendor.setVendorId(existingVendor.getVendorId());

        Vendor savedVendor = vendorRepository.save(updatedVendor);
        return vendorMapper.toDto(savedVendor);
    }

    @Transactional
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    private Pageable createPageable(int page, int size, String[] sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        String property = "vendorId";

        if (sort[0].contains(",")) {
            String[] _sort = sort[0].split(",");
            property = _sort[0];
            direction = Sort.Direction.fromString(_sort[1]);
        }

        return PageRequest.of(page, size, Sort.by(direction, property));
    }

    private PaginatedResponseDto<VendorDto> createPaginatedResponse(List<VendorDto> vendorDtos, Page<Vendor> vendorPage) {
        PaginatedResponseDto<VendorDto> response = new PaginatedResponseDto<>();
        response.setItems(vendorDtos);
        response.setCurrentPage(vendorPage.getNumber());
        response.setItemsPerPage(vendorPage.getSize());
        response.setTotalItems(vendorPage.getTotalElements());
        response.setTotalPages(vendorPage.getTotalPages());
        return response;
    }
}