package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.BankAccountDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.BankAccount;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.BankAccountMapper;
import com.quantumbooks.core.repository.BankAccountRepository;
import com.quantumbooks.core.specification.BankAccountSpecification;
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
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public PaginatedResponseDto<BankAccountDto> getBankAccounts(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<BankAccount> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(BankAccountSpecification.searchBankAccounts(search));
        }

        Page<BankAccount> bankAccountPage = bankAccountRepository.findAll(spec, pageable);

        List<BankAccountDto> bankAccounts = bankAccountPage.getContent().stream()
                .map(bankAccountMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(bankAccounts, bankAccountPage);
    }

    @Transactional
    public BankAccountDto createBankAccount(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDto);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(savedBankAccount);
    }

    public BankAccountDto getBankAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Account not found with id: " + id));
        return bankAccountMapper.toDto(bankAccount);
    }

    @Transactional
    public BankAccountDto updateBankAccount(Long id, BankAccountDto bankAccountDto) {
        BankAccount existingBankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Account not found with id: " + id));

        BankAccount updatedBankAccount = bankAccountMapper.toEntity(bankAccountDto);
        updatedBankAccount.setBankAccountId(existingBankAccount.getBankAccountId());

        BankAccount savedBankAccount = bankAccountRepository.save(updatedBankAccount);
        return bankAccountMapper.toDto(savedBankAccount);
    }

    @Transactional
    public void deleteBankAccount(Long id) {
        if (!bankAccountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bank Account not found with id: " + id);
        }
        bankAccountRepository.deleteById(id);
    }
}