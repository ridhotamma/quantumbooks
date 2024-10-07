package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BankAccountDto;
import com.quantumbooks.core.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    @Mapping(target = "status", expression = "java(bankAccount.getStatus().name())")
    BankAccountDto toDto(BankAccount bankAccount);

    @Mapping(target = "status", expression = "java(toAccountStatus(dto.getStatus()))")
    BankAccount toEntity(BankAccountDto dto);

    default BankAccount.AccountStatus toAccountStatus(String status) {
        try {
            return BankAccount.AccountStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}