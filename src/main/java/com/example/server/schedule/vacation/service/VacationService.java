package com.example.server.schedule.vacation.service;


import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception400;
import com.example.server._core.errors.exception.Exception404;
import com.example.server.schedule.vacation.dto.VacationRequest;
import com.example.server.schedule.vacation.dto.VacationResponse;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.schedule.vacation.model.VacationInfo;
import com.example.server.schedule.vacation.repository.VacationInfoRepository;
import com.example.server.schedule.vacation.repository.VacationRepository;
import com.example.server.user.model.User;
import com.example.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacationService {

    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;
    private final VacationInfoRepository vacationInfoRepository;

    @Transactional
    public VacationResponse.VacationDTO requestVacation(VacationRequest.AddDTO vacationRequest, Long userId) {

        if (vacationRequest == null) throw new Exception400(ErrorMessage.EMPTY_DATA_TO_REQUEST_VACATION);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404(ErrorMessage.USER_NOT_FOUND));

        Vacation vacation = vacationRequest.toEntityWith(user);

        VacationInfo vacationInfo = vacationInfoRepository.findByUser(user)
                .orElseThrow(() -> new Exception404(ErrorMessage.VACATION_INFO_NOT_FOUND));

        long vacationDays = Duration.between(vacation.getStartDate().toLocalDateTime(),
                vacation.getEndDate().toLocalDateTime()).toDays();

        if (vacationInfo.getRemainVacation() < vacationDays) {
            throw new Exception400(ErrorMessage.NOT_ENOUGH_REMAINING_VACATION_DAYS);
        }

        vacationInfo.updateInfo((int) vacationDays);

        Vacation savedVacation = vacationRepository.save(vacation);
        return VacationResponse.VacationDTO.from(savedVacation);
    }
}