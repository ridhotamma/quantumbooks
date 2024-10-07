package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class InvoiceSpecification {
    public static Specification<Invoice> searchBy(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("customer").get("customerName")), likePattern),
                    cb.like(cb.lower(root.get("invoiceID").as(String.class)), likePattern),
                    cb.like(cb.lower(root.get("totalAmount").as(String.class)), likePattern)
            );
        };
    }

    public static Specification<Invoice> hasStatus(Invoice.InvoiceStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Invoice> totalAmountGreaterThan(BigDecimal amount) {
        return (root, query, cb) -> cb.greaterThan(root.get("totalAmount"), amount);
    }

    public static Specification<Invoice> totalAmountLessThan(BigDecimal amount) {
        return (root, query, cb) -> cb.lessThan(root.get("totalAmount"), amount);
    }
}