package com.ttogal.api.controller.user;

import com.ttogal.api.controller.user.dto.request.UserRegisterRequestDto;
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
public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterRequestDto dto) {
  return new ResponseEntity<>(HttpStatus.OK);
}
}
