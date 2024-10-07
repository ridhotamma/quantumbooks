package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "JournalEntries")
@Data
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "PostedBy")
    private User postedBy;

    @Column(nullable = false)
    private LocalDateTime postedDate;
}