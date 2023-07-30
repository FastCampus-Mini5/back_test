package com.example.server.schedule.vacation.dto;

import com.example.server.schedule.Status;
import com.example.server.schedule.vacation.enums.Reason;
import com.example.server.schedule.vacation.model.Vacation;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacationResponse {

    @Getter
    @Builder
    public static class VacationDTO {

        private Long id;
        private String username;
        private Reason reason;
        private Status status;
        private Timestamp startDate;
        private Timestamp endDate;
        private Timestamp approvalDate;

        public static VacationDTO from(Vacation vacation) {
            return VacationDTO.builder()
                    .id(vacation.getId())
                    .username(vacation.getUser().getUsername())
                    .reason(vacation.getReason())
                    .status(vacation.getStatus())
                    .startDate(vacation.getStartDate())
                    .endDate(vacation.getEndDate())
                    .approvalDate(vacation.getApprovalDate())
                    .build();
        }
    }
}


