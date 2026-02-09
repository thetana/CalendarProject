package com.calendarproject.comment.dto;

import java.time.LocalDateTime;

public record CreateCommentResponse(Long id, Long scheduleId, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
