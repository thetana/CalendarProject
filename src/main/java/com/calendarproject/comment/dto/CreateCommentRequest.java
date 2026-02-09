package com.calendarproject.comment.dto;

public record CreateCommentRequest(Long scheduleId, String content, String writer, String password) {
}
