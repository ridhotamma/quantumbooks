package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.AccountDto;
import com.quantumbooks.core.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "parentAccountId", source = "parentAccount.accountId")
    AccountDto toDto(Account account);

    @Mapping(target = "parentAccount", ignore = true)
    Account toEntity(AccountDto accountDto);
}