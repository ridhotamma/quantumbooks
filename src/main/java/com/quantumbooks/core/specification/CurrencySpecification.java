package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Currency;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecification {
    public static Specification<Currency> searchCurrencies(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("currencyCode")), likeSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("currencyName")), likeSearch)
            );
        };
    }
}