package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.JournalEntryLineDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.JournalEntryLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journal-entry-lines")
@RequiredArgsConstructor
public class JournalEntryLineController {
    private final JournalEntryLineService journalEntryLineService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponseDto<JournalEntryLineDto>> getJournalEntryLines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "lineId,asc") String[] sort) {

        PaginatedResponseDto<JournalEntryLineDto> response = journalEntryLineService.getJournalEntryLines(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JournalEntryLineDto> createJournalEntryLine(@Valid @RequestBody JournalEntryLineDto journalEntryLineDto) {
        JournalEntryLineDto createdJournalEntryLine = journalEntryLineService.createJournalEntryLine(journalEntryLineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJournalEntryLine);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JournalEntryLineDto> getJournalEntryLineById(@PathVariable Long id) {
        JournalEntryLineDto journalEntryLine = journalEntryLineService.getJournalEntryLineById(id);
        return ResponseEntity.ok(journalEntryLine);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JournalEntryLineDto> updateJournalEntryLine(@PathVariable Long id, @Valid @RequestBody JournalEntryLineDto journalEntryLineDto) {
        JournalEntryLineDto updatedJournalEntryLine = journalEntryLineService.updateJournalEntryLine(id, journalEntryLineDto);
        return ResponseEntity.ok(updatedJournalEntryLine);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJournalEntryLine(@PathVariable Long id) {
        journalEntryLineService.deleteJournalEntryLine(id);
        return ResponseEntity.noContent().build();
    }
}