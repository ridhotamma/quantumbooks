package com.quantumbooks.core.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDto<T> {
    private List<T> items;
    private int currentPage;
    private int itemsPerPage;
    private long totalItems;
    private int totalPages;
}
