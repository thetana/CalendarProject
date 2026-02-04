package com.calendarproject.dto;

import java.time.LocalDateTime;

public record CreateCommentResponse(Long id, String content, String writer, Long scheduleId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
