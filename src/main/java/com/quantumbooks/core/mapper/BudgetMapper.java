package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.BudgetDto;
import com.quantumbooks.core.entity.Budget;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {
    Budget toEntity(BudgetDto dto);

    BudgetDto toDto(Budget entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BudgetDto dto, @MappingTarget Budget entity);
}