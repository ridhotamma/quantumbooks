package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Vendor;
import org.springframework.data.jpa.domain.Specification;

public class VendorSpecification {
    public static Specification<Vendor> searchByVendorName(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("vendorName")),
                    "%" + search.toLowerCase() + "%"
            );
        };
    }
}