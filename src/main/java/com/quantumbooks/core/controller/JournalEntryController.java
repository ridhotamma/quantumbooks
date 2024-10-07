package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.JournalEntryDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/journal-entries")
@RequiredArgsConstructor
public class JournalEntryController {
    private final JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<JournalEntryDto>> getJournalEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDate,
            @RequestParam(defaultValue = "entryId,asc") String[] sort) {

        PaginatedResponseDto<JournalEntryDto> response = journalEntryService.getJournalEntries(page, size, description, entryDate, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<JournalEntryDto> createJournalEntry(@Valid @RequestBody JournalEntryDto journalEntryDto) {
        JournalEntryDto createdJournalEntry = journalEntryService.createJournalEntry(journalEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJournalEntry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryDto> getJournalEntryById(@PathVariable Long id) {
        JournalEntryDto journalEntry = journalEntryService.getJournalEntryById(id);
        return ResponseEntity.ok(journalEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryDto> updateJournalEntry(@PathVariable Long id, @Valid @RequestBody JournalEntryDto journalEntryDto) {
        JournalEntryDto updatedJournalEntry = journalEntryService.updateJournalEntry(id, journalEntryDto);
        return ResponseEntity.ok(updatedJournalEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable Long id) {
        journalEntryService.deleteJournalEntry(id);
        return ResponseEntity.noContent().build();
    }
}
