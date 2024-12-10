package com.ttogal.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
  private SecretKey secretKey;
  @Value("${jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpirationPeriod;

  @Value("${jwt.access.header}")
  private String accessHeader;

  @Value("${jwt.refresh.header}")
  private String refreshHeader;

  private static final String BEARER = "Bearer ";

  public JwtUtil(@Value("${jwt.secretKey}")String secret){
    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public String getUsername(String token){
    return getPayload(token).get("username", String.class);
  }

  public String getRole(String token){
    return getPayload(token).get("role", String.class);
  }

  public Boolean isTokenExpired(String token){
    return getPayload(token).getExpiration().before(new Date());
  }

  private Claims getPayload(String token) {
    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public String createAccessToken(String username, String role) {
  return Jwts.builder()
          .claim("category","AccessToken")
          .claim("username",username)
          .claim("role",role)
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis()+accessTokenExpirationPeriod))
          .signWith(secretKey)
          .compact();
  }

  public String createRefreshToken(String username, String role) {
    return Jwts.builder()
            .claim("category","RefrestToken")
            .claim("username",username)
            .claim("role",role)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+refreshTokenExpirationPeriod))
            .compact();
  }

  public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    setAccessTokenHeader(response,accessToken);
    setRefreshTokenCookie(response,refreshToken);
  }

  private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
  response.addHeader(accessHeader, BEARER + accessToken);
  }
  private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie(refreshHeader, refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge((int) (refreshTokenExpirationPeriod / 1000));
    response.addCookie(cookie);
  }
}
