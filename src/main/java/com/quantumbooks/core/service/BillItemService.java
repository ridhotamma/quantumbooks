package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BillItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.BillItem;
import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BillItemMapper;
import com.quantumbooks.core.repository.BillItemRepository;
import com.quantumbooks.core.repository.BillRepository;
import com.quantumbooks.core.specification.BillItemSpecification;
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
public class BillItemService {
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;
    private final BillItemMapper billItemMapper;

    public PaginatedResponseDto<BillItemDto> getBillItems(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<BillItem> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BillItemSpecification.withDescriptionOrBillId(search));
        }

        Page<BillItem> billItemPage = billItemRepository.findAll(spec, pageable);

        List<BillItemDto> billItems = billItemPage.getContent().stream()
                .map(billItemMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(billItems, billItemPage);
    }

    @Transactional
    public BillItemDto createBillItem(BillItemDto billItemDto) {
        BillItem billItem = billItemMapper.toEntity(billItemDto);
        Bill bill = billRepository.findById(billItemDto.getBillId())
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billItemDto.getBillId()));
        billItem.setBill(bill);
        BillItem savedBillItem = billItemRepository.save(billItem);
        return billItemMapper.toDto(savedBillItem);
    }

    public BillItemDto getBillItemById(Long id) {
        BillItem billItem = billItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill item not found with id: " + id));
        return billItemMapper.toDto(billItem);
    }

    @Transactional
    public BillItemDto updateBillItem(Long id, BillItemDto billItemDto) {
        BillItem existingBillItem = billItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill item not found with id: " + id));

        BillItem updatedBillItem = billItemMapper.toEntity(billItemDto);
        updatedBillItem.setItemId(existingBillItem.getItemId());

        if (!existingBillItem.getBill().getBillId().equals(billItemDto.getBillId())) {
            Bill bill = billRepository.findById(billItemDto.getBillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billItemDto.getBillId()));
            updatedBillItem.setBill(bill);
        } else {
            updatedBillItem.setBill(existingBillItem.getBill());
        }

        BillItem savedBillItem = billItemRepository.save(updatedBillItem);
        return billItemMapper.toDto(savedBillItem);
    }

    @Transactional
    public void deleteBillItem(Long id) {
        if (!billItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill item not found with id: " + id);
        }
        billItemRepository.deleteById(id);
    }
}