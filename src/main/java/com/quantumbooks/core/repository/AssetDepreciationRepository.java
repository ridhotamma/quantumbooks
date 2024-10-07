package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.AssetDepreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDepreciationRepository extends JpaRepository<AssetDepreciation, Long>, JpaSpecificationExecutor<AssetDepreciation> {
}