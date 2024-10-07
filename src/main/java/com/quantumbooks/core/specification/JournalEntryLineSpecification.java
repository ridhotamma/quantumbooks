package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.JournalEntryLine;
import com.quantumbooks.core.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class JournalEntryLineSpecification {
    public static Specification<JournalEntryLine> searchByAccountName(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("account").get("accountName")),
                    "%" + search.toLowerCase() + "%"
            );
        };
    }
}