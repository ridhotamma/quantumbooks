package com.quantumbooks.core.specification;

import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.entity.UserRole;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> searchByUsernameOrEmail(String searchTerm) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("username")), "%" + searchTerm.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("email")), "%" + searchTerm.toLowerCase() + "%")
                );
    }

    public static Specification<User> hasRole(UserRole role) {
        return (root, query, cb) -> cb.equal(root.get("role"), role);
    }
}
