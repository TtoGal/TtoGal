package com.ttogal.api.controller.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyCodeRequestDto {

    @Email
    private String email;

    @NotBlank
    private String authCode;

}
