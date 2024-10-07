package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.TaxTransaction;
import org.springframework.data.jpa.domain.Specification;

public class TaxTransactionSpecification {
    public static Specification<TaxTransaction> searchTaxTransactions(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("transactionId").as(String.class), likeSearch),
                    criteriaBuilder.like(root.get("taxRate").get("taxName").as(String.class), likeSearch),
                    criteriaBuilder.like(root.get("transactionDate").as(String.class), likeSearch),
                    criteriaBuilder.like(root.get("taxableAmount").as(String.class), likeSearch),
                    criteriaBuilder.like(root.get("taxAmount").as(String.class), likeSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), likeSearch)
            );
        };
    }
}