package com.ttogal.api.controller.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ValidateEmailRequestDto(
        @Schema(description = "사용자의 이메일 주소", example = "example@naver.com")
        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email
) {
}
