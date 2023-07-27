package com.example.server.user.dto;

import com.example.server.user.model.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class SignUpDTO {

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        private String email;

        @NotBlank
        @Size(min = 2, max = 15)
        private String username;

        @NotBlank
        @Size(min = 4, max = 15)
        private String password;


        public User toEntityWithHashPassword(PasswordEncoder passwordEncoder) {
            String encodedPassword = passwordEncoder.encode(this.password);
            return User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .email(email)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class LoginDTO {

        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }
}
