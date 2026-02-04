package com.calendarproject.dto;

import java.time.LocalDateTime;

public record UpdateScheduleResponse(Long id, String title, String writer, LocalDateTime modifiedAt) {
}
