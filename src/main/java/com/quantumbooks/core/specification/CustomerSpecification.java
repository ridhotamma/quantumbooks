package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.Customer;
import com.quantumbooks.core.entity.Customer.CustomerStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {
    public static Specification<Customer> searchBy(String search) {
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("customerName")), "%" + search.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("phone")), "%" + search.toLowerCase() + "%")
        );
    }

    public static Specification<Customer> hasStatus(CustomerStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
