package com.calendarproject.user.dto;

import java.time.LocalDateTime;

public record UpdateUserResponse(Long id, String name, LocalDateTime modifiedAt) {
}
