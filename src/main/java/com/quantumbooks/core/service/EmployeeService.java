package com.quantumbooks.core.service;

import com.opencsv.CSVWriter;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
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

    @Transactional
    public ByteArrayInputStream exportToExcel() throws IOException {
        List<Employee> employees = employeeRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "First Name", "Last Name", "Email", "Hire Date", "Position", "Department", "Salary"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(employee.getEmployeeId());
                row.createCell(1).setCellValue(employee.getFirstName());
                row.createCell(2).setCellValue(employee.getLastName());
                row.createCell(3).setCellValue(employee.getEmail());
                row.createCell(4).setCellValue(employee.getHireDate().toString());
                row.createCell(5).setCellValue(employee.getPosition());
                row.createCell(6).setCellValue(employee.getDepartment());
                row.createCell(7).setCellValue(employee.getSalary().doubleValue());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Transactional
    public ByteArrayInputStream exportToCsv() throws IOException {
        List<Employee> employees = employeeRepository.findAll();

        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter)) {

            csvWriter.writeNext(new String[]{"ID", "First Name", "Last Name", "Email", "Hire Date", "Position", "Department", "Salary"});

            for (Employee employee : employees) {
                csvWriter.writeNext(new String[]{
                        employee.getEmployeeId().toString(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getHireDate().toString(),
                        employee.getPosition(),
                        employee.getDepartment(),
                        employee.getSalary().toString()
                });
            }

            return new ByteArrayInputStream(stringWriter.toString().getBytes());
        }
    }
}