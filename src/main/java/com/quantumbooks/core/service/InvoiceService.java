package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.InvoiceDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Invoice;
import com.quantumbooks.core.mapper.InvoiceMapper;
import com.quantumbooks.core.repository.InvoiceRepository;
import com.quantumbooks.core.specification.InvoiceSpecification;
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
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<InvoiceDto> getInvoices(int page, int size, String search, Invoice.InvoiceStatus status, String[] sort) {
        Specification<Invoice> spec = Specification.where(null);
        if (search != null && !search.isEmpty()) {
            spec = spec.and(InvoiceSpecification.searchBy(search));
        }
        if (status != null) {
            spec = spec.and(InvoiceSpecification.hasStatus(status));
        }

        Sort sortOrder = Sort.by(Sort.Direction.ASC, "invoiceID");
        if (sort != null && sort.length > 0) {
            sortOrder = Sort.by(sort[0], sort[1]);
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Invoice> invoicePage = invoiceRepository.findAll(spec, pageable);

        List<InvoiceDto> invoiceDtos = invoicePage.getContent().stream()
                .map(invoiceMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponseDto<InvoiceDto> response = new PaginatedResponseDto<>();
        response.setItems(invoiceDtos);
        response.setCurrentPage(invoicePage.getNumber());
        response.setItemsPerPage(invoicePage.getSize());
        response.setTotalItems(invoicePage.getTotalElements());
        response.setTotalPages(invoicePage.getTotalPages());

        return response;
    }

    @Transactional
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    @Transactional(readOnly = true)
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return invoiceMapper.toDto(invoice);
    }

    @Transactional
    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoiceMapper.updateInvoiceFromDto(invoiceDto, invoice);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}