package com.ttogal.domain.user.entity.constant;

import com.ttogal.domain.user.entity.enums.Gender;
import com.ttogal.domain.user.entity.enums.UserJobStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
public record RegisterRequest() {

    @NotNull(message = "이름을 입력해주세요.")
    private static String name;

    @Email
    @NotNull(message = "이메일 주소를 입력해주세요.")
    private static String email;

    @NotNull(message = "인증 번호를 입력해주세요.")
    private static String validationCode; // 이메일 인증 번호 입력

    @NotNull(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이내로 입력해주세요.")
    @Pattern(regexp = "^[^\\s]*$", message = "닉네임은 공백 없이 입력해주세요.")
    private static String nickname;

    @NotNull(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])|(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d])|(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=.*\\d)|(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z]).*$",
            message = "영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합/공백 불가")
    private static String password;

    @NotNull(message = "비밀번호를 다시 한 번 입력해주세요")
    private static String checkPassword;

    @NotNull(message = "현재 상태를 입력해주세요.")
    private static UserJobStatus userJobStatus;

    @NotNull(message = "생년월일을 입력해주세요.")
    private static String birthDate;

    @NotNull(message = "성별을 선택해주세요.")
    private static Gender gender;

    private static boolean skipOnboarding;

}
