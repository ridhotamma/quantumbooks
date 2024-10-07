package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.AuditLogDto;
import com.quantumbooks.core.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    @Mapping(source = "user.id", target = "userId")
    AuditLogDto toDto(AuditLog entity);

    @Mapping(source = "userId", target = "user.id")
    AuditLog toEntity(AuditLogDto dto);
}