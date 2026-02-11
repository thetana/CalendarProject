package com.calendarproject.schedule.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record GetPageResponse(
        List<GetSchedulesResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static GetPageResponse from(Page<GetSchedulesResponse> page) {
        return new GetPageResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
