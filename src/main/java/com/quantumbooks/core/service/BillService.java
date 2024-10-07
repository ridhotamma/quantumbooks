package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BillDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.entity.Vendor;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BillMapper;
import com.quantumbooks.core.repository.BillRepository;
import com.quantumbooks.core.repository.VendorRepository;
import com.quantumbooks.core.specification.BillSpecification;
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
public class BillService {
    private final BillRepository billRepository;
    private final VendorRepository vendorRepository;
    private final BillMapper billMapper;

    public PaginatedResponseDto<BillDto> getBills(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Bill> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BillSpecification.withBillIdOrVendorName(search));
        }

        Page<Bill> billPage = billRepository.findAll(spec, pageable);

        List<BillDto> bills = billPage.getContent().stream()
                .map(billMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(bills, billPage);
    }

    @Transactional
    public BillDto createBill(BillDto billDto) {
        Bill bill = billMapper.toEntity(billDto);
        Vendor vendor = vendorRepository.findById(billDto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + billDto.getVendorId()));
        bill.setVendor(vendor);
        Bill savedBill = billRepository.save(bill);
        return billMapper.toDto(savedBill);
    }

    public BillDto getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
        return billMapper.toDto(bill);
    }

    @Transactional
    public BillDto updateBill(Long id, BillDto billDto) {
        Bill existingBill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));

        Bill updatedBill = billMapper.toEntity(billDto);
        updatedBill.setBillId(existingBill.getBillId());

        if (!existingBill.getVendor().getVendorId().equals(billDto.getVendorId())) {
            Vendor vendor = vendorRepository.findById(billDto.getVendorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + billDto.getVendorId()));
            updatedBill.setVendor(vendor);
        } else {
            updatedBill.setVendor(existingBill.getVendor());
        }

        Bill savedBill = billRepository.save(updatedBill);
        return billMapper.toDto(savedBill);
    }

    @Transactional
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill not found with id: " + id);
        }
        billRepository.deleteById(id);
    }
}