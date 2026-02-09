package com.calendarproject.schedule.dto;

import java.time.LocalDateTime;

public record UpdateScheduleResponse(Long id, Long userId, String title, String details, LocalDateTime modifiedAt) {
}
