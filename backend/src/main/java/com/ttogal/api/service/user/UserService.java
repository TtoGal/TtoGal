package com.ttogal.api.service.user;

import com.ttogal.api.controller.user.dto.request.UserRegisterRequestDto;
import com.ttogal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public void register(UserRegisterRequestDto dto) {

  }
}
