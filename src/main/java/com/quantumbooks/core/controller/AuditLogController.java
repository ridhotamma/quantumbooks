package com.quantumbooks.core.controller;

import com.quantumbooks.core.dto.AuditLogDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.service.AuditLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<AuditLogDto>> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "logId,asc") String[] sort) {
        PaginatedResponseDto<AuditLogDto> response = auditLogService.getAuditLogs(page, size, search, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AuditLogDto> createAuditLog(@Valid @RequestBody AuditLogDto auditLogDto) {
        AuditLogDto createdAuditLog = auditLogService.createAuditLog(auditLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuditLog);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDto> getAuditLogById(@PathVariable Long id) {
        AuditLogDto auditLog = auditLogService.getAuditLogById(id);
        return ResponseEntity.ok(auditLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditLogDto> updateAuditLog(@PathVariable Long id, @Valid @RequestBody AuditLogDto auditLogDto) {
        AuditLogDto updatedAuditLog = auditLogService.updateAuditLog(id, auditLogDto);
        return ResponseEntity.ok(updatedAuditLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {
        auditLogService.deleteAuditLog(id);
        return ResponseEntity.noContent().build();
    }
}