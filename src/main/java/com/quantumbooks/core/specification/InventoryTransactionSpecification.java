package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.InventoryTransaction;
import org.springframework.data.jpa.domain.Specification;

public class InventoryTransactionSpecification {
    public static Specification<InventoryTransaction> searchInventoryTransactions(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("item").get("itemName")), likePattern),
                    cb.like(cb.lower(root.get("transactionType").as(String.class)), likePattern)
            );
        };
    }
}