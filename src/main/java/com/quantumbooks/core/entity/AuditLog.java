package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AuditLog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime actionDate;

    @Column(nullable = false, length = 50)
    private String actionType;

    @Column(nullable = false, length = 50)
    private String tableName;

    private Long recordId;

    @Column(columnDefinition = "TEXT")
    private String oldValue;

    @Column(columnDefinition = "TEXT")
    private String newValue;
}