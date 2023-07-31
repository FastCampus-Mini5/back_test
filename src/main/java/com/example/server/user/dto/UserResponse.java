package com.example.server.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

public class UserResponse {

    @Getter
    @Builder
    public static class AvailableEmailDTO {
        private String email;
        private Boolean available;
    }

    @Getter
    @Builder
    public static class UserInfoDTO {
        private String username;
        private String email;
        private String profileImage;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private Timestamp hireDate;
    }
}
