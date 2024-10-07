package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.PayrollTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollTransactionRepository extends JpaRepository<PayrollTransaction, Long>, JpaSpecificationExecutor<PayrollTransaction> {
}