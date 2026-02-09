package com.calendarproject.auth.dto;

import java.time.LocalDateTime;

public record AuthSignupResponse(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
