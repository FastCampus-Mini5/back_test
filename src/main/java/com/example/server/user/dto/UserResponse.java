package com.example.server.user.dto;

import lombok.Builder;
import lombok.Getter;

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
        private String hireDate;
    }
}
