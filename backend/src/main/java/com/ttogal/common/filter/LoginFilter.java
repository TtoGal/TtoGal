package com.ttogal.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttogal.api.controller.user.dto.request.LoginRequestDto;
import com.ttogal.api.controller.user.dto.response.CustomUserDetails;
import com.ttogal.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
  private final static String REQUEST_URL = "/api/v1/users/login";
  private final static String HTTP_METHOD = "POST";

  private static final AntPathRequestMatcher PATH_REQUEST_MATCHER = new AntPathRequestMatcher(REQUEST_URL, HTTP_METHOD);

  private final ObjectMapper objectMapper;
  private final JwtUtil jwtUtil;

  public LoginFilter(ObjectMapper objectMapper, JwtUtil jwtUtil) {
    super(PATH_REQUEST_MATCHER);
    this.objectMapper = objectMapper;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
    LoginRequestDto dto = objectMapper.readValue(request.getInputStream(),
            LoginRequestDto.class);
    log.info("인증 시도 유저:{}", dto.email());
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
    return this.getAuthenticationManager().authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    String username = extractUsername(authentication);
    String role = extractRole(authentication);

    String accessToken = jwtUtil.createAccessToken(username, role);
    String refrestToken = jwtUtil.createRefreshToken(username, role);

    jwtUtil.sendAccessAndRefreshToken(response, accessToken, refrestToken);

    log.info("로그인 성공: {}", username);
    log.info("Access & refresh 토큰 생성");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    response.setStatus(401);
  }

  private String extractUsername(Authentication authentication) {
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    return customUserDetails.getUsername();
  }

  private String extractRole(Authentication authentication) {
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();
    return role;
  }
}
