package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.JournalEntry;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class JournalEntrySpecification {
    public static Specification<JournalEntry> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%");
    }

    public static Specification<JournalEntry> hasEntryDate(LocalDate entryDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("entryDate"), entryDate);
    }
}