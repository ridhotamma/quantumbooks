package com.quantumbooks.core.mapper;

import com.quantumbooks.core.dto.JournalEntryDto;
import com.quantumbooks.core.entity.JournalEntry;
import com.quantumbooks.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalEntryMapper {
    @Mapping(target = "postedBy", source = "postedBy.id")
    JournalEntryDto toDto(JournalEntry journalEntry);

    @Mapping(target = "postedBy", ignore = true)
    JournalEntry toEntity(JournalEntryDto journalEntryDto);

    default User mapIdToUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}