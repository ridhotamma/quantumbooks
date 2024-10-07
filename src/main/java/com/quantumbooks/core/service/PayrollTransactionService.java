package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.PayrollTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.PayrollTransaction;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.PayrollTransactionMapper;
import com.quantumbooks.core.repository.PayrollTransactionRepository;
import com.quantumbooks.core.specification.PayrollTransactionSpecification;
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
public class PayrollTransactionService {
    private final PayrollTransactionRepository payrollTransactionRepository;
    private final PayrollTransactionMapper payrollTransactionMapper;

    public PaginatedResponseDto<PayrollTransactionDto> getPayrollTransactions(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<PayrollTransaction> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(PayrollTransactionSpecification.searchPayrollTransactions(search));
        }

        Page<PayrollTransaction> payrollTransactionPage = payrollTransactionRepository.findAll(spec, pageable);

        List<PayrollTransactionDto> payrollTransactions = payrollTransactionPage.getContent().stream()
                .map(payrollTransactionMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(payrollTransactions, payrollTransactionPage);
    }

    @Transactional
    public PayrollTransactionDto createPayrollTransaction(PayrollTransactionDto payrollTransactionDto) {
        PayrollTransaction payrollTransaction = payrollTransactionMapper.toEntity(payrollTransactionDto);
        PayrollTransaction savedPayrollTransaction = payrollTransactionRepository.save(payrollTransaction);
        return payrollTransactionMapper.toDto(savedPayrollTransaction);
    }

    public PayrollTransactionDto getPayrollTransactionById(Long id) {
        PayrollTransaction payrollTransaction = payrollTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll Transaction not found with id: " + id));
        return payrollTransactionMapper.toDto(payrollTransaction);
    }

    @Transactional
    public PayrollTransactionDto updatePayrollTransaction(Long id, PayrollTransactionDto payrollTransactionDto) {
        PayrollTransaction existingPayrollTransaction = payrollTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll Transaction not found with id: " + id));

        PayrollTransaction updatedPayrollTransaction = payrollTransactionMapper.toEntity(payrollTransactionDto);
        updatedPayrollTransaction.setTransactionId(existingPayrollTransaction.getTransactionId());

        PayrollTransaction savedPayrollTransaction = payrollTransactionRepository.save(updatedPayrollTransaction);
        return payrollTransactionMapper.toDto(savedPayrollTransaction);
    }

    @Transactional
    public void deletePayrollTransaction(Long id) {
        if (!payrollTransactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payroll Transaction not found with id: " + id);
        }
        payrollTransactionRepository.deleteById(id);
    }
}