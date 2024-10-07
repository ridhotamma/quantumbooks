package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.BankTransaction;
import org.springframework.data.jpa.domain.Specification;

public class BankTransactionSpecification {
    public static Specification<BankTransaction> searchBankTransactions(String search) {
        return (root, query, criteriaBuilder) -> {
            String likeTerm = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likeTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("bankAccount").get("accountName")), likeTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("bankAccount").get("accountNumber")), likeTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("bankAccount").get("bankName")), likeTerm)
            );
        };
    }
}