package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.PayrollTransaction;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PayrollTransactionSpecification {
    public static Specification<PayrollTransaction> searchPayrollTransactions(String search) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            BigDecimal searchAmount = null;
            try {
                searchAmount = new BigDecimal(search);
            } catch (NumberFormatException ignored) {
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employee").get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employee").get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.function("DATE_FORMAT", String.class, root.get("payPeriodStart"), criteriaBuilder.literal("%Y-%m-%d")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.function("DATE_FORMAT", String.class, root.get("payPeriodEnd"), criteriaBuilder.literal("%Y-%m-%d")), likePattern),
                    searchAmount != null ? criteriaBuilder.equal(root.get("grossPay"), searchAmount) : criteriaBuilder.conjunction(),
                    searchAmount != null ? criteriaBuilder.equal(root.get("deductions"), searchAmount) : criteriaBuilder.conjunction(),
                    searchAmount != null ? criteriaBuilder.equal(root.get("netPay"), searchAmount) : criteriaBuilder.conjunction()
            );
        };
    }
}