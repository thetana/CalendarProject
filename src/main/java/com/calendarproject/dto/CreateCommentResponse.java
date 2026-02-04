package com.calendarproject.dto;

import java.time.LocalDateTime;

public record CreateCommentResponse(Long id, Long scheduleId, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
