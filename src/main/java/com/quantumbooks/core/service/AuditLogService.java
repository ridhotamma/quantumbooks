package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.AuditLogDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.AuditLog;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.AuditLogMapper;
import com.quantumbooks.core.repository.AuditLogRepository;
import com.quantumbooks.core.specification.AuditLogSpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public PaginatedResponseDto<AuditLogDto> getAuditLogs(int page, int size, String search, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<AuditLog> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(AuditLogSpecification.searchAuditLogs(search));
        }

        Page<AuditLog> auditLogPage = auditLogRepository.findAll(spec, pageable);

        List<AuditLogDto> auditLogs = auditLogPage.getContent().stream()
                .map(auditLogMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(auditLogs, auditLogPage);
    }

    @Transactional
    public AuditLogDto createAuditLog(AuditLogDto auditLogDto) {
        AuditLog auditLog = auditLogMapper.toEntity(auditLogDto);
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        return auditLogMapper.toDto(savedAuditLog);
    }

    public AuditLogDto getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit Log not found with id: " + id));
        return auditLogMapper.toDto(auditLog);
    }

    @Transactional
    public AuditLogDto updateAuditLog(Long id, AuditLogDto auditLogDto) {
        AuditLog existingAuditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit Log not found with id: " + id));

        AuditLog updatedAuditLog = auditLogMapper.toEntity(auditLogDto);
        updatedAuditLog.setLogId(existingAuditLog.getLogId());

        AuditLog savedAuditLog = auditLogRepository.save(updatedAuditLog);
        return auditLogMapper.toDto(savedAuditLog);
    }

    @Transactional
    public void deleteAuditLog(Long id) {
        if (!auditLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Audit Log not found with id: " + id);
        }
        auditLogRepository.deleteById(id);
    }
}