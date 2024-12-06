package com.ttogal.api.controller.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ttogal.domain.user.entity.User;
import com.ttogal.domain.user.entity.constant.Gender;
import com.ttogal.domain.user.entity.constant.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserRegisterRequestDto(
        @Schema(description = "사용자의 이름", example = "홍길동")
        @NotNull(message = "이름을 입력해주세요.")
        String name,

        @Schema(description = "사용자의 이메일 주소", example = "example@naver.com")
        @Email
        @NotNull(message = "이메일 주소를 입력해주세요.")
        String email,

        @Schema(description = "사용자의 닉네임 (2~12자)", example = "길동이")
        @NotNull(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이내로 입력해주세요.")
        @Pattern(regexp = "^[^\\s]*$", message = "닉네임은 공백 없이 입력해주세요.")
        String nickname,

        @Schema(description = "비밀번호 (최소 8자, 영문/숫자/특수문자 중 2가지 이상 조합)",example = "Password123")
        @NotNull(message = "비밀번호를 입력해주세요")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])|(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d])|(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=.*\\d)|(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z]).*$",
                message = "영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합/공백 불가")
        String password,

        @Schema(description = "현재 재직 상태 (예: STUDENT, EMPLOYEE, FREELANCER, JOB_SEEKING, ENTREPRENEUR, ON_LEAVE, OTHERS)", example = "JOB_SEEKING")
        @NotNull(message = "현재 재직 상태를 입력해주세요.")
        JobStatus jobStatus,

        @Schema(description = "생년월일 (yyyy-MM-dd 형식)", example = "2001-01-01")
        @NotNull(message = "생년월일을 yyyy-MM-dd 형식으로 입력해주세요.")
        @Past
        LocalDate birthDate,

        @Schema(description = "성별 (예: MALE, FEMALE)", example = "MALE")
        @NotNull(message = "성별을 선택해주세요.")
        Gender gender
) {
  public User toEntity(String password) {
    return User.builder()
            .name(this.name)
            .email(this.email)
            .nickname(this.nickname)
            .password(password)
            .jobStatus(this.jobStatus)
            .birthDate(this.birthDate)
            .gender(this.gender)
            .build();
  }
}
