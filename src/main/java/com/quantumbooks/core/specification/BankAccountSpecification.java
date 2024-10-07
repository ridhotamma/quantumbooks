package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.BankAccount;
import org.springframework.data.jpa.domain.Specification;

public class BankAccountSpecification {
    public static Specification<BankAccount> searchBankAccounts(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isEmpty()) {
                return cb.conjunction();
            }
            String lowercaseSearch = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("accountName")), lowercaseSearch),
                    cb.like(cb.lower(root.get("accountNumber")), lowercaseSearch),
                    cb.like(cb.lower(root.get("bankName")), lowercaseSearch)
            );
        };
    }
}