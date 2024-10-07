package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long>, JpaSpecificationExecutor<TaxRate> {
}