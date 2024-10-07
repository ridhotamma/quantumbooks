package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.FixedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedAssetRepository extends JpaRepository<FixedAsset, Long>, JpaSpecificationExecutor<FixedAsset> {
}