package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.PayrollTransactionDto;
import com.quantumbooks.core.entity.PayrollTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PayrollTransactionMapper {
    @Mapping(source = "employee.employeeId", target = "employeeId")
    PayrollTransactionDto toDto(PayrollTransaction entity);

    @Mapping(source = "employeeId", target = "employee.employeeId")
    PayrollTransaction toEntity(PayrollTransactionDto dto);
}