package com.example.server.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Builder
    public static class AvailableEmailDTO {
        private String email;
        @Setter
        private Boolean available;
    }
}
