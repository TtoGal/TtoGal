package com.ttogal.api.controller.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ttogal.domain.user.entity.User;
import com.ttogal.domain.user.entity.constant.Gender;
import com.ttogal.domain.user.entity.constant.JobStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserRegisterRequestDto(
        @NotNull(message = "이름을 입력해주세요.")
        String name,

        @Email
        @NotNull(message = "이메일 주소를 입력해주세요.")
        String email,

        @NotNull(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이내로 입력해주세요.")
        @Pattern(regexp = "^[^\\s]*$", message = "닉네임은 공백 없이 입력해주세요.")
        String nickname,

        @NotNull(message = "비밀번호를 입력해주세요")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])|(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d])|(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=.*\\d)|(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z]).*$",
                message = "영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합/공백 불가")
        String password,

        @NotNull(message = "비밀번호를 다시 한 번 입력해주세요")
        String checkPassword,

        @NotNull(message = "현재 상태를 입력해주세요.")
        JobStatus jobStatus,

        @NotNull(message = "생년월일을 yyyy-MM-dd 형식으로 입력해주세요.")
        @Past
        LocalDate birthDate,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender
) {
  public User toEntity() {
    return User.builder()
            .name(this.name)
            .email(this.email)
            .nickname(this.nickname)
            .password(this.password)
            .jobStatus(this.jobStatus)
            .birthDate(this.birthDate)
            .gender(this.gender)
            .build();
  }
}
