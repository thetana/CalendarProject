package com.calendarproject.schedule.dto;

import com.calendarproject.comment.dto.GetCommentsResponse;
import com.calendarproject.schedule.entity.Schedule;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record GetSchedulesResponse(Long id, Long userId, String title, String details, LocalDateTime createdAt,
                                   LocalDateTime modifiedAt) {
    public static GetSchedulesResponse from(Schedule page) {
        return new GetSchedulesResponse(
                page.getId(),
                page.getUserId(),
                page.getTitle(),
                page.getDetails(),
                page.getCreatedAt(),
                page.getModifiedAt()
        );
    }
}