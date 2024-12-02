package com.ttogal.api.controller.user.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ValidateNicknameRequestDto(
        @NotNull(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이내로 입력해주세요.")
        @Pattern(regexp = "^[^\\s]*$", message = "닉네임은 공백 없이 입력해주세요.")
        String nickname
) {
}
