package com.calendarproject.schedule.dto;

import java.time.LocalDateTime;

public record GetSchedulesResponse(Long id, String title, String details, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
