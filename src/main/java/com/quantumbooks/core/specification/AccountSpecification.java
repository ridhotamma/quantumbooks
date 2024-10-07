package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<Account> searchByAccountName(String accountName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("accountName")),
                        "%" + accountName.toLowerCase() + "%");
    }
}