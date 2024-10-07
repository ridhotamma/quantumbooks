package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.CustomerDto;
import com.quantumbooks.core.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
    void updateCustomerFromDto(CustomerDto customerDto, @MappingTarget Customer customer);
}