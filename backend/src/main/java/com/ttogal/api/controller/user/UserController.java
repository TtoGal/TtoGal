package com.ttogal.api.controller.user;

import com.ttogal.api.controller.user.dto.request.UserRegisterRequestDto;
import com.ttogal.api.controller.user.dto.request.ValidateEmailRequestDto;
import com.ttogal.api.controller.user.dto.response.UserRegisterResponseDto;
import com.ttogal.api.controller.user.dto.response.UserResponseDto;
import com.ttogal.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterRequestDto dto) {
    Long userId=userService.register(dto);
    UserRegisterResponseDto responseDto=UserRegisterResponseDto.builder()
            .userId(userId)
            .message("회원가입이 성공적으로 완료되었습니다.")
            .build();
    return new ResponseEntity<>(responseDto,HttpStatus.OK);
  }

  @PostMapping("/validate-email")
  public ResponseEntity<UserResponseDto> validateEmail(@RequestBody @Valid ValidateEmailRequestDto requestdto) {
    userService.validateEmail(requestdto.email());
    UserResponseDto responseDto=UserResponseDto.builder().message("이메일 검증이 완료되었습니다.").build();
    return new ResponseEntity<>(responseDto,HttpStatus.OK);
  }

  @PostMapping("/validate-nickname")
  public ResponseEntity<UserResponseDto> validateNickname(@RequestBody @Valid UserRegisterRequestDto requestdto) {
    userService.validateNickname(requestdto.nickname());
    UserResponseDto responseDto=UserResponseDto.builder().message("닉네임 검증이 완료되었습니다.").build();
    return new ResponseEntity<>(responseDto,HttpStatus.OK);
  }
}
