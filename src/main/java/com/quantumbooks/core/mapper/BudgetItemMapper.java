package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BudgetItemDto;
import com.quantumbooks.core.entity.BudgetItem;
import com.quantumbooks.core.entity.Budget;
import com.quantumbooks.core.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetItemMapper {
    @Mapping(target = "budget", source = "budgetId")
    @Mapping(target = "account", source = "accountId")
    BudgetItem toEntity(BudgetItemDto dto);

    @Mapping(target = "budgetId", source = "budget.budgetId")
    @Mapping(target = "accountId", source = "account.accountId")
    BudgetItemDto toDto(BudgetItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BudgetItemDto dto, @MappingTarget BudgetItem entity);

    default Budget mapIdToBudget(Long id) {
        if (id == null) {
            return null;
        }
        Budget budget = new Budget();
        budget.setBudgetId(id);
        return budget;
    }

    default Account mapIdToAccount(Long id) {
        if (id == null) {
            return null;
        }
        Account account = new Account();
        account.setAccountId(id);
        return account;
    }

    default Long mapBudgetToId(Budget budget) {
        return budget != null ? budget.getBudgetId() : null;
    }

    default Long mapAccountToId(Account account) {
        return account != null ? account.getAccountId() : null;
    }
}