package com.calendarproject.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateScheduleRequest(
        @NotBlank(message = "title을 입력해주세요!") @Size(max = 50, message = "title을 50자 이내로 입력해 주세요") String title,
        @NotBlank(message = "details을 입력해주세요!") @Size(max = 500, message = "details을 500자 이내로 입력해 주세요") String details) {
}