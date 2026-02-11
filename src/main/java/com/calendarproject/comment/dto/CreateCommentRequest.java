package com.calendarproject.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateCommentRequest(
        @NotBlank(message = "content를 입력해주세요!") @Size(max = 50, message = "content를 50자 이내로 입력해 주세요") String content) {
}
