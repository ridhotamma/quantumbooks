package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long>, JpaSpecificationExecutor<BudgetItem> {
}