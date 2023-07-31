package com.example.server.schedule.vacation.dto;

import com.example.server.schedule.vacation.enums.Reason;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.user.model.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacationRequest {

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @ToString
        public static class AddDTO {

                @NotNull
                private Reason reason;

                @NotNull
                private Timestamp startDate;

                @NotNull
                private Timestamp endDate;

                public Vacation toEntityWith(User user) {
                        return Vacation.builder()
                                .user(user)
                                .reason(reason)
                                .startDate(startDate)
                                .endDate(endDate)
                                .build();
                }
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @ToString
        public static class CancelDTO {
                @NotNull
                private Long id;
        }
}