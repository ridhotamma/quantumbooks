package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.AccountDto;
import com.quantumbooks.core.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BudgetItemMapper.class})
public interface AccountMapper {
    @Mapping(target = "parentAccountId", source = "parentAccount.accountId")
    @Mapping(target = "budgetItems", source = "budgetItems")
    AccountDto toDto(Account account);

    @Mapping(target = "parentAccount", ignore = true)
    Account toEntity(AccountDto accountDto);
}