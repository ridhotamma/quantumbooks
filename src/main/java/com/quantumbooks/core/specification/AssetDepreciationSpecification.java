package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.AssetDepreciation;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssetDepreciationSpecification {
    public static Specification<AssetDepreciation> searchAssetDepreciations(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("asset").get("assetName")), likePattern),
                    cb.like(root.get("depreciationDate").as(String.class), likePattern),
                    cb.like(root.get("depreciationAmount").as(String.class), likePattern),
                    cb.like(root.get("accumulatedDepreciation").as(String.class), likePattern)
            );
        };
    }

    public static Specification<AssetDepreciation> betweenDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> cb.between(root.get("depreciationDate"), startDate, endDate);
    }

    public static Specification<AssetDepreciation> greaterThanDepreciationAmount(BigDecimal amount) {
        return (root, query, cb) -> cb.greaterThan(root.get("depreciationAmount"), amount);
    }
}