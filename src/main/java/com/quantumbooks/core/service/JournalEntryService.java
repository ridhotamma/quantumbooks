package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.JournalEntryDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.JournalEntry;
import com.quantumbooks.core.entity.User;
import com.quantumbooks.core.exception.ResourceNotFoundException;
import com.quantumbooks.core.mapper.JournalEntryMapper;
import com.quantumbooks.core.repository.JournalEntryRepository;
import com.quantumbooks.core.repository.UserRepository;
import com.quantumbooks.core.specification.JournalEntrySpecification;
import com.quantumbooks.core.util.PaginationSortingUtils;
import com.quantumbooks.core.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;
    private final JournalEntryMapper journalEntryMapper;

    public PaginatedResponseDto<JournalEntryDto> getJournalEntries(int page, int size, String description, LocalDate entryDate, String[] sort) {
        Pageable pageable = PaginationSortingUtils.createPageable(page, size, sort);
        Specification<JournalEntry> spec = Specification.where(null);

        if (description != null && !description.isEmpty()) {
            spec = spec.and(JournalEntrySpecification.hasDescription(description));
        }
        if (entryDate != null) {
            spec = spec.and(JournalEntrySpecification.hasEntryDate(entryDate));
        }

        Page<JournalEntry> journalEntryPage = journalEntryRepository.findAll(spec, pageable);

        List<JournalEntryDto> journalEntries = journalEntryPage.getContent().stream()
                .map(journalEntryMapper::toDto)
                .toList();

        return PaginationUtils.createPaginatedResponse(journalEntries, journalEntryPage);
    }

    @Transactional
    public JournalEntryDto createJournalEntry(JournalEntryDto journalEntryDto) {
        JournalEntry journalEntry = journalEntryMapper.toEntity(journalEntryDto);
        User postedBy = userRepository.findById(journalEntryDto.getPostedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + journalEntryDto.getPostedBy()));
        journalEntry.setPostedBy(postedBy);
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        return journalEntryMapper.toDto(savedJournalEntry);
    }

    public JournalEntryDto getJournalEntryById(Long id) {
        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found with id: " + id));
        return journalEntryMapper.toDto(journalEntry);
    }

    @Transactional
    public JournalEntryDto updateJournalEntry(Long id, JournalEntryDto journalEntryDto) {
        JournalEntry existingJournalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found with id: " + id));

        JournalEntry updatedJournalEntry = journalEntryMapper.toEntity(journalEntryDto);
        updatedJournalEntry.setEntryId(existingJournalEntry.getEntryId());

        User postedBy = userRepository.findById(journalEntryDto.getPostedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + journalEntryDto.getPostedBy()));
        updatedJournalEntry.setPostedBy(postedBy);

        JournalEntry savedJournalEntry = journalEntryRepository.save(updatedJournalEntry);
        return journalEntryMapper.toDto(savedJournalEntry);
    }

    @Transactional
    public void deleteJournalEntry(Long id) {
        if (!journalEntryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Journal entry not found with id: " + id);
        }
        journalEntryRepository.deleteById(id);
    }
}
