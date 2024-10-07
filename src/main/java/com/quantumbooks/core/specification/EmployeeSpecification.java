package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> searchEmployees(String search) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("position")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("department")), likePattern)
            );
        };
    }
}