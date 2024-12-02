package com.ttogal.api.controller.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ValidateEmailRequestDto(
        @NotNull(message = "이메일 주소를 입력해주세요.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email
) {
}
