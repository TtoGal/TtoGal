package com.ttogal.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
            .csrf((auth)->auth.disable());
    http
            .formLogin((auth)->auth.disable());
    http
            .httpBasic((auth)->auth.disable());
    http
            .authorizeHttpRequests((auth)->auth
                    .requestMatchers("/login","/","/register").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated());
    http
            .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/v3/api-docs/**",         // OpenAPI 문서 경로
            "/swagger-ui/**",          // Swagger UI 경로
            "/swagger-ui/index.html",  // Swagger UI 인덱스
            "/webjars/**",
            "/index.html",
            "/h2-console/**"
    );// Swagger UI에 필요한 웹 자원와 어플리케이션 정적 리소스)
  }
}
