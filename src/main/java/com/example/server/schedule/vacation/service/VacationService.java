package com.example.server.schedule.vacation.service;


import com.example.server._core.errors.ErrorMessage;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.schedule.vacation.model.VacationInfo;
import com.example.server.schedule.vacation.repository.VacationInfoRepository;
import com.example.server.schedule.vacation.repository.VacationRepository;
import com.example.server.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class VacationService {

    private final VacationRepository vacationRepository;
    private final VacationInfoRepository vacationInfoRepository;
    @Transactional
    public Vacation requestVacation(Vacation vacationRequest) {
        VacationInfo vacationInfo = vacationInfoRepository.findByUser(vacationRequest.getUser())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.VACATION_INFO_NOT_FOUND));

        long vacationDays = Duration.between(vacationRequest.getStartDate().toLocalDateTime(),
                vacationRequest.getEndDate().toLocalDateTime()).toDays();

        if (vacationInfo.getRemainVacation() < vacationDays) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_REMAINING_VACATION_DAYS);
        }

        vacationInfo.setRemainVacation(vacationInfo.getRemainVacation() - (int) vacationDays);
        vacationInfo.setUsedVacation(vacationInfo.getUsedVacation() + (int) vacationDays);

        return vacationRepository.save(vacationRequest);
    }
}