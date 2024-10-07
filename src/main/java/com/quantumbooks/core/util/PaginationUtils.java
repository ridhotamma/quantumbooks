package com.quantumbooks.core.util;

import com.quantumbooks.core.dto.PaginatedResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationUtils {

    public static <T> PaginatedResponseDto<T> createPaginatedResponse(List<T> items, Page<?> page) {
        PaginatedResponseDto<T> response = new PaginatedResponseDto<>();
        response.setItems(items);
        response.setCurrentPage(page.getNumber());
        response.setItemsPerPage(page.getSize());
        response.setTotalItems(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }
}
