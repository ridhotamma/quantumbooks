package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.FixedAsset;
import org.springframework.data.jpa.domain.Specification;

public class FixedAssetSpecification {
    public static Specification<FixedAsset> searchFixedAssets(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("assetName")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("depreciationMethod")), likePattern)
            );
        };
    }
}