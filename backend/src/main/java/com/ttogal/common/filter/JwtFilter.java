package com.ttogal.common.filter;

import com.ttogal.api.controller.user.dto.response.CustomUserDetails;
import com.ttogal.common.util.JwtUtil;
import com.ttogal.domain.user.entity.User;
import com.ttogal.domain.user.entity.constant.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorization= request.getHeader("Authorization");

  if(authorization==null || !authorization.startsWith("Bearer ")) {
    System.out.println("token null");
    filterChain.doFilter(request, response);
    return;
  }

  String token = authorization.split(" ")[1];

  if(jwtUtil.isTokenExpired(token)){
    System.out.println("token expired");
    filterChain.doFilter(request, response);
    return;
  }

  String username = jwtUtil.getUsername(token);
  String tempPassword="tempPassword";
  String role = jwtUtil.getRole(token);

  User user=User.builder().email(username)
          .password(tempPassword)
          .role(Role.valueOf(role.toUpperCase()))
          .build();

    CustomUserDetails customUserDetails = new CustomUserDetails(user);
    Authentication authToken=new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);
    filterChain.doFilter(request, response);
  }
}
