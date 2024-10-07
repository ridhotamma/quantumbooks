package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.InventoryItem;
import org.springframework.data.jpa.domain.Specification;

public class InventoryItemSpecification {
    public static Specification<InventoryItem> searchInventoryItems(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("itemName")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern)
            );
        };
    }
}