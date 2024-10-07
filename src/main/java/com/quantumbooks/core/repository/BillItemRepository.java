package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillItemRepository extends JpaRepository<BillItem, Long>, JpaSpecificationExecutor<BillItem> {
}