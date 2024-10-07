package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.TaxRate;
import org.springframework.data.jpa.domain.Specification;

public class TaxRateSpecification {
    public static Specification<TaxRate> searchTaxRates(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("taxName")), likeSearch),
                    criteriaBuilder.like(root.get("rate").as(String.class), likeSearch),
                    criteriaBuilder.like(root.get("effectiveDate").as(String.class), likeSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), likeSearch)
            );
        };
    }
}