package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.CustomerDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Customer;
import com.quantumbooks.core.entity.Customer.CustomerStatus;
import com.quantumbooks.core.mapper.CustomerMapper;
import com.quantumbooks.core.repository.CustomerRepository;
import com.quantumbooks.core.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<CustomerDto> getCustomers(int page, int size, String search, CustomerStatus status, String[] sort) {
        Specification<Customer> spec = Specification.where(null);
        if (search != null && !search.isEmpty()) {
            spec = spec.and(CustomerSpecification.searchBy(search));
        }
        if (status != null) {
            spec = spec.and(CustomerSpecification.hasStatus(status));
        }

        Sort sortOrder = Sort.by(Sort.Direction.ASC, "customerID");
        if (sort != null && sort.length > 0) {
            sortOrder = Sort.by(sort[0], sort[1]);
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

        List<CustomerDto> customerDtos = customerPage.getContent().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponseDto<CustomerDto> response = new PaginatedResponseDto<>();
        response.setItems(customerDtos);
        response.setCurrentPage(customerPage.getNumber());
        response.setItemsPerPage(customerPage.getSize());
        response.setTotalItems(customerPage.getTotalElements());
        response.setTotalPages(customerPage.getTotalPages());

        return response;
    }

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toDto(customer);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerMapper.updateCustomerFromDto(customerDto, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}