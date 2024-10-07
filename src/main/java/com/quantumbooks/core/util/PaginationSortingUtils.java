package com.quantumbooks.core.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PaginationSortingUtils {

    public static Pageable createPageable(int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

    private static Sort.Direction getSortDirection(String direction) {
        return direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
