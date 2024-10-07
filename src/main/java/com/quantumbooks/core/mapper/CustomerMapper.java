package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.CustomerDto;
import com.quantumbooks.core.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}