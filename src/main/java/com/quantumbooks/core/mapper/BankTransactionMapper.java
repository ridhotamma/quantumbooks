package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BankTransactionDto;
import com.quantumbooks.core.entity.BankTransaction;
import com.quantumbooks.core.entity.BankAccount;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankTransactionMapper {
    @Mapping(target = "bankAccount", source = "bankAccountId")
    BankTransaction toEntity(BankTransactionDto dto);

    @Mapping(target = "bankAccountId", source = "bankAccount.bankAccountId")
    BankTransactionDto toDto(BankTransaction entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BankTransactionDto dto, @MappingTarget BankTransaction entity);

    default BankAccount mapIdToBankAccount(Long id) {
        if (id == null) {
            return null;
        }
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(id);
        return bankAccount;
    }
}