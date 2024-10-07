package com.quantumbooks.core.service;

import com.quantumbooks.core.dto.JournalEntryLineDto;
import com.quantumbooks.core.dto.PaginatedResponseDto;
import com.quantumbooks.core.entity.JournalEntryLine;
import com.quantumbooks.core.mapper.JournalEntryLineMapper;
import com.quantumbooks.core.repository.JournalEntryLineRepository;
import com.quantumbooks.core.specification.JournalEntryLineSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalEntryLineService {
    private final JournalEntryLineRepository journalEntryLineRepository;
    private final JournalEntryLineMapper journalEntryLineMapper;

    @Transactional(readOnly = true)
    public PaginatedResponseDto<JournalEntryLineDto> getJournalEntryLines(int page, int size, String search, String[] sort) {
        Specification<JournalEntryLine> spec = JournalEntryLineSpecification.searchByAccountName(search);
        Pageable pageable = createPageable(page, size, sort);

        Page<JournalEntryLine> journalEntryLinePage = journalEntryLineRepository.findAll(spec, pageable);

        List<JournalEntryLineDto> journalEntryLineDtos = journalEntryLinePage.getContent().stream()
                .map(journalEntryLineMapper::toDto)
                .collect(Collectors.toList());

        return createPaginatedResponse(journalEntryLineDtos, journalEntryLinePage);
    }

    @Transactional
    public JournalEntryLineDto createJournalEntryLine(JournalEntryLineDto journalEntryLineDto) {
        JournalEntryLine journalEntryLine = journalEntryLineMapper.toEntity(journalEntryLineDto);
        JournalEntryLine savedJournalEntryLine = journalEntryLineRepository.save(journalEntryLine);
        return journalEntryLineMapper.toDto(savedJournalEntryLine);
    }

    @Transactional(readOnly = true)
    public JournalEntryLineDto getJournalEntryLineById(Long id) {
        JournalEntryLine journalEntryLine = journalEntryLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JournalEntryLine not found"));
        return journalEntryLineMapper.toDto(journalEntryLine);
    }

    @Transactional
    public JournalEntryLineDto updateJournalEntryLine(Long id, JournalEntryLineDto journalEntryLineDto) {
        JournalEntryLine existingJournalEntryLine = journalEntryLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JournalEntryLine not found"));

        JournalEntryLine updatedJournalEntryLine = journalEntryLineMapper.toEntity(journalEntryLineDto);
        updatedJournalEntryLine.setLineId(existingJournalEntryLine.getLineId());

        JournalEntryLine savedJournalEntryLine = journalEntryLineRepository.save(updatedJournalEntryLine);
        return journalEntryLineMapper.toDto(savedJournalEntryLine);
    }

    @Transactional
    public void deleteJournalEntryLine(Long id) {
        journalEntryLineRepository.deleteById(id);
    }

    private Pageable createPageable(int page, int size, String[] sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        String property = "lineId";

        if (sort[0].contains(",")) {
            String[] _sort = sort[0].split(",");
            property = _sort[0];
            direction = Sort.Direction.fromString(_sort[1]);
        }

        return PageRequest.of(page, size, Sort.by(direction, property));
    }

    private PaginatedResponseDto<JournalEntryLineDto> createPaginatedResponse(List<JournalEntryLineDto> journalEntryLineDtos, Page<JournalEntryLine> journalEntryLinePage) {
        PaginatedResponseDto<JournalEntryLineDto> response = new PaginatedResponseDto<>();
        response.setItems(journalEntryLineDtos);
        response.setCurrentPage(journalEntryLinePage.getNumber());
        response.setItemsPerPage(journalEntryLinePage.getSize());
        response.setTotalItems(journalEntryLinePage.getTotalElements());
        response.setTotalPages(journalEntryLinePage.getTotalPages());
        return response;
    }
}