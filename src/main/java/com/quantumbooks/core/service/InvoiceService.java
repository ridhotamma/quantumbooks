package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.InvoiceDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Invoice;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.InvoiceMapper;
import com.quantumbooks.core.repository.InvoiceRepository;
import com.quantumbooks.core.specification.InvoiceSpecification;
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
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public PaginatedResponseDto<InvoiceDto> getInvoices(int page, int size, String search, Invoice.InvoiceStatus status, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Invoice> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(InvoiceSpecification.searchBy(search));
        }
        if (status != null) {
            spec = spec.and(InvoiceSpecification.hasStatus(status));
        }

        Page<Invoice> invoicePage = invoiceRepository.findAll(spec, pageable);

        List<InvoiceDto> invoices = invoicePage.getContent().stream()
                .map(invoiceMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(invoices, invoicePage);
    }

    @Transactional
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(savedInvoice);
    }

    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.toDto(invoice);
    }

    @Transactional
    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        Invoice updatedInvoice = invoiceMapper.toEntity(invoiceDto);
        updatedInvoice.setInvoiceID(existingInvoice.getInvoiceID());

        Invoice savedInvoice = invoiceRepository.save(updatedInvoice);
        return invoiceMapper.toDto(savedInvoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}