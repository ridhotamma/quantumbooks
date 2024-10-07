package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BillDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.entity.Vendor;
import com.quantumbooks.core.mapper.BillMapper;
import com.quantumbooks.core.repository.BillRepository;
import com.quantumbooks.core.repository.VendorRepository;
import com.quantumbooks.core.specification.BillSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final VendorRepository vendorRepository;
    private final BillMapper billMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<BillDto> getBills(int page, int size, String search, String[] sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        String property = "billId";
        if (sort[0].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
            property = sort[1];
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));
        Page<Bill> billPage = billRepository.findAll(BillSpecification.withBillIdOrVendorName(search), pageable);

        List<BillDto> billDtos = billPage.getContent().stream()
                .map(billMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponseDto<BillDto> response = new PaginatedResponseDto<>();
        response.setItems(billDtos);
        response.setCurrentPage(billPage.getNumber());
        response.setItemsPerPage(billPage.getSize());
        response.setTotalItems(billPage.getTotalElements());
        response.setTotalPages(billPage.getTotalPages());

        return response;
    }

    @Transactional
    public BillDto createBill(BillDto billDto) {
        Bill bill = billMapper.toEntity(billDto);
        Vendor vendor = vendorRepository.findById(billDto.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        bill.setVendor(vendor);
        Bill savedBill = billRepository.save(bill);
        return billMapper.toDto(savedBill);
    }

    @Transactional(readOnly = true)
    public BillDto getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        return billMapper.toDto(bill);
    }

    @Transactional
    public BillDto updateBill(Long id, BillDto billDto) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        billMapper.updateEntityFromDto(billDto, bill);
        if (!bill.getVendor().getVendorId().equals(billDto.getVendorId())) {
            Vendor vendor = vendorRepository.findById(billDto.getVendorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
            bill.setVendor(vendor);
        }
        Bill updatedBill = billRepository.save(bill);
        return billMapper.toDto(updatedBill);
    }

    @Transactional
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill not found");
        }
        billRepository.deleteById(id);
    }
}