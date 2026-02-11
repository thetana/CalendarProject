package com.calendarproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "name을 입력해주세요!") @Size(max = 20, message = "name을 20자 이내로 입력해 주세요") String name,
        String password) {
}
