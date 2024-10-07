package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.BillItem;
import org.springframework.data.jpa.domain.Specification;

public class BillItemSpecification {
    public static Specification<BillItem> withDescriptionOrBillId(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.function("text", String.class, root.get("bill").get("billId")), "%" + search + "%")
            );
        };
    }
}