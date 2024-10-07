package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BankTransactionDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.BankTransaction;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BankTransactionMapper;
import com.quantumbooks.core.repository.BankTransactionRepository;
import com.quantumbooks.core.specification.BankTransactionSpecification;
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
public class BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;
    private final BankTransactionMapper bankTransactionMapper;

    public PaginatedResponseDto<BankTransactionDto> getBankTransactions(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<BankTransaction> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BankTransactionSpecification.searchBankTransactions(search));
        }

        Page<BankTransaction> bankTransactionPage = bankTransactionRepository.findAll(spec, pageable);

        List<BankTransactionDto> bankTransactions = bankTransactionPage.getContent().stream()
                .map(bankTransactionMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(bankTransactions, bankTransactionPage);
    }

    @Transactional
    public BankTransactionDto createBankTransaction(BankTransactionDto bankTransactionDto) {
        BankTransaction bankTransaction = bankTransactionMapper.toEntity(bankTransactionDto);
        BankTransaction savedBankTransaction = bankTransactionRepository.save(bankTransaction);
        return bankTransactionMapper.toDto(savedBankTransaction);
    }

    public BankTransactionDto getBankTransactionById(Long id) {
        BankTransaction bankTransaction = bankTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Transaction not found with id: " + id));
        return bankTransactionMapper.toDto(bankTransaction);
    }

    @Transactional
    public BankTransactionDto updateBankTransaction(Long id, BankTransactionDto bankTransactionDto) {
        BankTransaction existingBankTransaction = bankTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Transaction not found with id: " + id));

        bankTransactionMapper.updateEntityFromDto(bankTransactionDto, existingBankTransaction);
        BankTransaction savedBankTransaction = bankTransactionRepository.save(existingBankTransaction);
        return bankTransactionMapper.toDto(savedBankTransaction);
    }

    @Transactional
    public void deleteBankTransaction(Long id) {
        if (!bankTransactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bank Transaction not found with id: " + id);
        }
        bankTransactionRepository.deleteById(id);
    }
}