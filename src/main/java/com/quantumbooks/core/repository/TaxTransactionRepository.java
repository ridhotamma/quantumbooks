package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.TaxTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxTransactionRepository extends JpaRepository<TaxTransaction, Long>, JpaSpecificationExecutor<TaxTransaction> {
}