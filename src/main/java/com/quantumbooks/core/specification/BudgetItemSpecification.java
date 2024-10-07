package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.BudgetItem;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BudgetItemSpecification {
    public static Specification<BudgetItem> searchBudgetItems(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeTerm = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("budget").get("budgetName")), likeTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("account").get("accountName")), likeTerm)
            );
        };
    }

    public static Specification<BudgetItem> hasBudgetId(Long budgetId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("budget").get("budgetId"), budgetId);
    }

    public static Specification<BudgetItem> hasAccountId(Long accountId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("account").get("accountId"), accountId);
    }

    public static Specification<BudgetItem> hasAmountGreaterThan(BigDecimal amount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("amount"), amount);
    }
}