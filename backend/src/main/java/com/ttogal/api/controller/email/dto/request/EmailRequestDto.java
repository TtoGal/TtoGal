package com.ttogal.api.controller.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

    @Email
    @NotEmpty
    @Schema(description = "인증 코드를 보낼 이메일 주소", example = "example@naver.com")
    private String email;

}
