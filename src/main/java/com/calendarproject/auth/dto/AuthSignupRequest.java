package com.calendarproject.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthSignupRequest(@Email(message = "올바른 이메일 형식이 아닙니다.") String email,
                                @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 영문, 숫자를 포함한 8자 이상이어야 합니다") String password,
                                @NotBlank(message = "name을 입력해주세요!") @Size(max = 20, message = "name을 20자 이내로 입력해 주세요") String name) {
}
