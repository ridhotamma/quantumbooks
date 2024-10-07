package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Budget;
import org.springframework.data.jpa.domain.Specification;

public class BudgetSpecification {
    public static Specification<Budget> searchBudgets(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeTerm = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("budgetName")), likeTerm),
                    criteriaBuilder.like(root.get("fiscalYear").as(String.class), likeTerm)
            );
        };
    }
}