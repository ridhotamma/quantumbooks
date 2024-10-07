package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>, JpaSpecificationExecutor<Budget> {
}