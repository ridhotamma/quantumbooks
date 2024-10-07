package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.JournalEntryLineDto;
import com.quantumbooks.core.entity.JournalEntryLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalEntryLineMapper {
    @Mapping(source = "journalEntry.entryId", target = "journalEntryId")
    @Mapping(source = "account.accountId", target = "accountId")
    JournalEntryLineDto toDto(JournalEntryLine journalEntryLine);

    @Mapping(source = "journalEntryId", target = "journalEntry.entryId")
    @Mapping(source = "accountId", target = "account.accountId")
    JournalEntryLine toEntity(JournalEntryLineDto journalEntryLineDto);
}