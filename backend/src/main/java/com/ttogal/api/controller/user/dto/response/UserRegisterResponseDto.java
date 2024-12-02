package com.ttogal.api.controller.user.dto.response;

import lombok.Builder;

@Builder
public record UserRegisterResponseDto(
        Long userId,
        String message
) {
}
