package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.EmployeeDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.Employee;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.EmployeeMapper;
import com.quantumbooks.core.repository.EmployeeRepository;
import com.quantumbooks.core.specification.EmployeeSpecification;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public PaginatedResponseDto<EmployeeDto> getEmployees(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<Employee> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(EmployeeSpecification.searchEmployees(search));
        }

        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

        List<EmployeeDto> employees = employeePage.getContent().stream()
                .map(employeeMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(employees, employeePage);
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        Employee updatedEmployee = employeeMapper.toEntity(employeeDto);
        updatedEmployee.setEmployeeId(existingEmployee.getEmployeeId());

        Employee savedEmployee = employeeRepository.save(updatedEmployee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}