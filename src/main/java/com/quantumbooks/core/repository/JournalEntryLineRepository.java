package com.quantumbooks.core.repository;

import com.quantumbooks.core.entity.JournalEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JournalEntryLineRepository extends JpaRepository<JournalEntryLine, Long>, JpaSpecificationExecutor<JournalEntryLine> {
}