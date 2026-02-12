package com.calendarproject.schedule.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record GetSchedulesResponse(Long id, String userName, String title, String details, Long commentCount,
                                   LocalDateTime createdAt,
                                   LocalDateTime modifiedAt) {
    @QueryProjection
    public GetSchedulesResponse {
    }
}