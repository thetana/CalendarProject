package com.calendarproject.comment.dto;

import java.time.LocalDateTime;

public record CreateCommentResponse(Long id, Long userId, Long scheduleId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
