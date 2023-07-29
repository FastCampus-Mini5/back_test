package com.example.server.user.dto;

import com.example.server.user.model.SignUp;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class SignUpDTO {

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "유효하지 않은 이메일 형식입니다.")
        private String email;

        @NotBlank
        @Size(min = 2, max = 15)
        private String username;

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자를 모두 포함해야 하며, 최소 8글자 이상이어야 합니다.")
        private String password;

        @NotBlank
        @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
        private String hireDate;

        public SignUp toEntityWithHashPassword(PasswordEncoder passwordEncoder) {
            String encodedPassword = passwordEncoder.encode(this.password);
            return SignUp.builder()
                    .username(username) // TODO : AES 암호화
                    .password(encodedPassword)
                    .email(email) // TODO : AES 암호화
                    .hireDate(Timestamp.valueOf(hireDate))
                    .build();
        }
    }

    @Getter
    @ToString
    public static class SignInDTO {

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "유효하지 않은 이메일 형식입니다.")
        private String email;

        @NotBlank
        private String password;
    }

    @Getter
    @ToString
    public static class CheckEmailDTO {
        @Setter
        @NotBlank
        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "유효하지 않은 이메일 주소입니다.")
        private String email;
    }
}
