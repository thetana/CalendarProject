package com.calendarproject.schedule.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record GetSchedulesResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static <T> GetSchedulesResponse<T> from(Page<T> page) {
        return new GetSchedulesResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
