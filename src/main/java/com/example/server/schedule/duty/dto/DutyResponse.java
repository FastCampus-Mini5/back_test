package com.example.server.schedule.duty.dto;

import com.example.server.schedule.Status;
import com.example.server.schedule.duty.model.Duty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DutyResponse {

    @Getter
    @Builder
    public static class DutyDTO {

        private Long id;
        private String userEmail;
        private Timestamp dutyDate;
        private Status status;

        public static DutyDTO from(Duty duty) {
            return DutyDTO.builder()
                    .id(duty.getId())
                    .userEmail(duty.getUser().getEmail())
                    .dutyDate(duty.getDutyDate())
                    .status(duty.getStatus())
                    .build();
        }
    }
}



