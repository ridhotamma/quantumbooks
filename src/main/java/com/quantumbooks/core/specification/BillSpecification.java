package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Bill;
import com.quantumbooks.core.entity.Vendor;
import org.springframework.data.jpa.domain.Specification;

public class BillSpecification {
    public static Specification<Bill> withBillIdOrVendorName(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.function("text", String.class, root.get("billId")), "%" + search + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("vendor").get("vendorName")), "%" + search.toLowerCase() + "%")
            );
        };
    }
}