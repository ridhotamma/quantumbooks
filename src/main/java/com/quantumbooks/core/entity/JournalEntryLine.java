package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "JournalEntryLines")
@Data
public class JournalEntryLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LineID")
    private Long lineId;

    @ManyToOne
    @JoinColumn(name = "EntryID", nullable = false)
    private JournalEntry journalEntry;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false)
    private Account account;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal debit;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal credit;
}