package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.CustomerDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Customer;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.CustomerMapper;
import com.quantumbooks.core.repository.CustomerRepository;
import com.quantumbooks.core.specification.CustomerSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public PaginatedResponseDto<CustomerDto> getCustomers(int page, int size, String search, Customer.CustomerStatus status, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Customer> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(CustomerSpecification.searchBy(search));
        }
        if (status != null) {
            spec = spec.and(CustomerSpecification.hasStatus(status));
        }

        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

        List<CustomerDto> customers = customerPage.getContent().stream()
                .map(customerMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(customers, customerPage);
    }

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDto(customer);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        Customer updatedCustomer = customerMapper.toEntity(customerDto);
        updatedCustomer.setCustomerID(existingCustomer.getCustomerID());

        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return customerMapper.toDto(savedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}