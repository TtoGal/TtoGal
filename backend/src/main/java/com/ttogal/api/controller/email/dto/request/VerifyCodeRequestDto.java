package com.ttogal.api.controller.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyCodeRequestDto {

    @Email
    @Schema(description = "인증을 진행할 이메일 주소", example = "example@naver.com")
    private String email;

    @NotBlank
    @Schema(description = "사용자가 입력한 인증 코드", example = "123456")
    private String authCode;

}
