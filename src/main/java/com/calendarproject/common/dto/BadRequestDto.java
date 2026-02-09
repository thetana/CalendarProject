package com.calendarproject.common.dto;

// 정합성 틀렸을 때 반환해줄 DTO 이다
public record BadRequestDto(boolean isOk, String message) {
}
