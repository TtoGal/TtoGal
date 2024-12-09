package com.ttogal.api.controller.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record LoginRequestDto(
        @Schema(description = "사용자의 이메일 주소", example = "example@naver.com")
        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "비밀번호 (최소 8자, 영문/숫자/특수문자 중 2가지 이상 조합)",example = "asdf1234!")
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password
) {
}
