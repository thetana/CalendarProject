package com.calendarproject.schedule.dto;

import java.time.LocalDateTime;

public record CreateScheduleResponse(Long id, Long userId, String title, String details, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
