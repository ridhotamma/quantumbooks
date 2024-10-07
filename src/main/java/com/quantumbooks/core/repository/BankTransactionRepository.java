package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long>, JpaSpecificationExecutor<BankTransaction> {
}