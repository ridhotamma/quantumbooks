package com.quantumbooks.core.config;

import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.entity.UserRole;
import com.quantumbooks.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuperAdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${superadmin.username}")
    private String superadminUsername;

    @Value("${superadmin.email}")
    private String superadminEmail;

    @Value("${superadmin.password}")
    private String superadminPassword;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeSuperadmin() {
        if (userRepository.findByUsername(superadminUsername).isEmpty()) {
            User superadmin = new User();
            superadmin.setUsername(superadminUsername);
            superadmin.setEmail(superadminEmail);
            superadmin.setPassword(passwordEncoder.encode(superadminPassword));
            superadmin.setRole(UserRole.ROLE_ADMIN);
            superadmin.setSuperAdmin(true);
            superadmin.setFullName("Super Admin");
            userRepository.save(superadmin);

            System.out.println("Superadmin user created successfully.");
        } else {
            System.out.println("Superadmin user already exists. Skipping creation.");
        }
    }
}