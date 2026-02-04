package com.calendarproject.dto;

import java.time.LocalDateTime;

public record GetScheduleResponse (Long id, String title, String details, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt){
}
