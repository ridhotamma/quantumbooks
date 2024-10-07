package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BillItemDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.BillItem;
import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.mapper.BillItemMapper;
import com.quantumbooks.core.repository.BillItemRepository;
import com.quantumbooks.core.repository.BillRepository;
import com.quantumbooks.core.specification.BillItemSpecification;
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
public class BillItemService {
    private final BillItemRepository billItemRepository;
    private final BillRepository billRepository;
    private final BillItemMapper billItemMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<BillItemDto> getBillItems(int page, int size, String search, String[] sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        String property = "itemId";
        if (sort[0].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
            property = sort[1];
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));
        Page<BillItem> billItemPage = billItemRepository.findAll(BillItemSpecification.withDescriptionOrBillId(search), pageable);

        List<BillItemDto> billItemDtos = billItemPage.getContent().stream()
                .map(billItemMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponseDto<BillItemDto> response = new PaginatedResponseDto<>();
        response.setItems(billItemDtos);
        response.setCurrentPage(billItemPage.getNumber());
        response.setItemsPerPage(billItemPage.getSize());
        response.setTotalItems(billItemPage.getTotalElements());
        response.setTotalPages(billItemPage.getTotalPages());

        return response;
    }

    @Transactional
    public BillItemDto createBillItem(BillItemDto billItemDto) {
        BillItem billItem = billItemMapper.toEntity(billItemDto);
        Bill bill = billRepository.findById(billItemDto.getBillId())
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        billItem.setBill(bill);
        BillItem savedBillItem = billItemRepository.save(billItem);
        return billItemMapper.toDto(savedBillItem);
    }

    @Transactional(readOnly = true)
    public BillItemDto getBillItemById(Long id) {
        BillItem billItem = billItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill item not found"));
        return billItemMapper.toDto(billItem);
    }

    @Transactional
    public BillItemDto updateBillItem(Long id, BillItemDto billItemDto) {
        BillItem billItem = billItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill item not found"));
        billItemMapper.updateEntityFromDto(billItemDto, billItem);
        if (!billItem.getBill().getBillId().equals(billItemDto.getBillId())) {
            Bill bill = billRepository.findById(billItemDto.getBillId())
                    .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
            billItem.setBill(bill);
        }
        BillItem updatedBillItem = billItemRepository.save(billItem);
        return billItemMapper.toDto(updatedBillItem);
    }

    @Transactional
    public void deleteBillItem(Long id) {
        if (!billItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill item not found");
        }
        billItemRepository.deleteById(id);
    }
}