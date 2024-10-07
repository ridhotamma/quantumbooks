package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.AccountDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Account;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.AccountMapper;
import com.quantumbooks.core.repository.AccountRepository;
import com.quantumbooks.core.specification.AccountSpecification;
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
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<AccountDto> getAccounts(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Account> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(AccountSpecification.searchByAccountName(search));
        }

        Page<Account> accountPage = accountRepository.findAll(spec, pageable);

        List<AccountDto> accounts = accountPage.getContent().stream()
                .map(accountMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(accounts, accountPage);
    }

    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        if (accountDto.getParentAccountId() != null) {
            Account parentAccount = accountRepository.findById(accountDto.getParentAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent account not found with id: " + accountDto.getParentAccountId()));
            account.setParentAccount(parentAccount);
        }
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        Account updatedAccount = accountMapper.toEntity(accountDto);
        updatedAccount.setAccountId(existingAccount.getAccountId());

        if (accountDto.getParentAccountId() != null) {
            Account parentAccount = accountRepository.findById(accountDto.getParentAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent account not found with id: " + accountDto.getParentAccountId()));
            updatedAccount.setParentAccount(parentAccount);
        }

        Account savedAccount = accountRepository.save(updatedAccount);
        return accountMapper.toDto(savedAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}