package com.ttogal.api.service.user;

import com.ttogal.api.controller.user.dto.response.CustomUserDetails;
import com.ttogal.domain.user.entity.User;
import com.ttogal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user=userRepository.findByEmail(username).orElseThrow(
            ()->new UsernameNotFoundException("해당 사용자가 존재하지 않습니다."));
    return new CustomUserDetails(user);
  }
}
