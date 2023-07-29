package com.example.server.schedule.duty.dto;

import com.example.server.schedule.duty.model.Duty;
import com.example.server.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class DutyRequest {
    @NotNull
    private Timestamp dutyDate;

    public Duty toDutyEntity(User user) {
        return Duty.builder()
                .user(user)
                .dutyDate(dutyDate)
                .build();
    }
}