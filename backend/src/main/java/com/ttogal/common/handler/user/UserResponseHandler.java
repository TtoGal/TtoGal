package com.ttogal.common.handler.user;

import com.ttogal.api.controller.user.dto.response.UserRegisterResponseDto;
import com.ttogal.api.controller.user.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserResponseHandler {

    public UserRegisterResponseDto createRegisterResponse(Long userId) {
        return UserRegisterResponseDto.builder()
                .userId(userId)
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }

    public UserResponseDto createUserResponse(String message) {
        return UserResponseDto.builder().message(message).build();
    }

}
