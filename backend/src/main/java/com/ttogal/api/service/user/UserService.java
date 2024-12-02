package com.ttogal.api.service.user;

import com.ttogal.api.controller.user.dto.request.UserRegisterRequestDto;
import com.ttogal.api.controller.user.dto.request.ValidateEmailRequestDto;
import com.ttogal.api.controller.user.dto.response.UserRegisterResponseDto;
import com.ttogal.api.controller.user.dto.response.UserResponseDto;
import com.ttogal.common.exception.ExceptionCode;
import com.ttogal.common.exception.user.UserException;
import com.ttogal.domain.user.entity.User;
import com.ttogal.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public Long register(UserRegisterRequestDto dto) {
    validateNickname(dto.nickname());
    validateEmail(dto.email());
    String password = bCryptPasswordEncoder.encode(dto.password());
    User user=dto.toEntity(password);
    userRepository.save(user);
    return user.getUserId();
  }

  public void validateEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new UserException(ExceptionCode.DUPLICATED_USER_EMAIL);
    }
  }

  public void validateNickname(String nickname) {
    if (userRepository.existsByNickname(nickname)) {
      throw new UserException(ExceptionCode.DUPLICATED_USER_NICKNAME);
    }
  }
}
