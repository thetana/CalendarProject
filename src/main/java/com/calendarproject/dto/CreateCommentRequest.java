package com.calendarproject.dto;

public record CreateCommentRequest(String content, String writer, Long scheduleId, String password) {
}
