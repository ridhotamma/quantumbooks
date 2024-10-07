package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.AuditLog;
import org.springframework.data.jpa.domain.Specification;

public class AuditLogSpecification {
    public static Specification<AuditLog> searchAuditLogs(String search) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("actionType")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("tableName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("oldValue")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("newValue")), likePattern)
            );
        };
    }
}