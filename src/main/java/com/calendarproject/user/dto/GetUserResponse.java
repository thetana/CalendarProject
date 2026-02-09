package com.calendarproject.user.dto;

import java.time.LocalDateTime;

public record GetUserResponse(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
