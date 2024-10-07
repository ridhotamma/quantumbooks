package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BudgetDto;
import com.quantumbooks.core.dto.BudgetDto.BudgetDetailDto;
import com.quantumbooks.core.entity.Budget;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BudgetItemMapper.class})
public interface BudgetMapper {
    Budget toEntity(BudgetDto dto);
    BudgetDto toDto(Budget entity);

    @Mapping(source = "budgetItems", target = "budgetItems")
    BudgetDetailDto toBudgetDetailDto(Budget entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BudgetDto dto, @MappingTarget Budget entity);
}