package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.EmployeeDto;
import com.quantumbooks.core.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee entity);
    Employee toEntity(EmployeeDto dto);
}