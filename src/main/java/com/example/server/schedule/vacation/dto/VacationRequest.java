package com.example.server.schedule.vacation.dto;

import com.example.server.schedule.vacation.enums.Reason;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class VacationRequest {

        @NotNull
        private Reason reason;

        @NotNull
        private Timestamp startDate;

        @NotNull
        private Timestamp endDate;

        public Vacation toVacationEntity(User user) {
                return Vacation.builder()
                        .user(user)
                        .reason(reason)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build();
        }
}