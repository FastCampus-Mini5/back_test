package com.example.server.schedule.vacation.service;


import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception400;
import com.example.server._core.errors.exception.Exception403;
import com.example.server._core.errors.exception.Exception404;
import com.example.server.schedule.Status;
import com.example.server.schedule.vacation.dto.VacationRequest;
import com.example.server.schedule.vacation.dto.VacationResponse;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.schedule.vacation.model.VacationInfo;
import com.example.server.schedule.vacation.repository.VacationInfoRepository;
import com.example.server.schedule.vacation.repository.VacationRepository;
import com.example.server.user.model.User;
import com.example.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class VacationService {

    private final UserService userService;
    private final VacationRepository vacationRepository;
    private final VacationInfoRepository vacationInfoRepository;

    @Transactional
    public VacationResponse.VacationDTO requestVacation(VacationRequest.AddDTO vacationRequest, Long userId) {

        if (vacationRequest == null) throw new Exception400(ErrorMessage.EMPTY_DATA_TO_REQUEST_VACATION);

        User user = userService.findUserById(userId);
        Vacation vacation = vacationRequest.toEntityWith(user);

        VacationInfo vacationInfo = vacationInfoRepository.findByUser(vacation.getUser())
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

    @Transactional
    public VacationResponse.VacationDTO cancelVacation(VacationRequest.CancelDTO cancelDTO, Long userId) {

        if (cancelDTO == null) throw new Exception400(ErrorMessage.EMPTY_DATA_TO_CANCEL_VACATION);

        Long id = cancelDTO.getId();
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new Exception404(ErrorMessage.VACATION_NOT_FOUND));

        if (!vacation.getUser().getId().equals(userId)) {
            throw new Exception403(ErrorMessage.UNAUTHORIZED_ACCESS_TO_VACATION);
        }

        if (vacation.getStatus() == Status.APPROVE) {
            throw new Exception403(ErrorMessage.VACATION_CANNOT_BE_CANCELLED);
        }

        vacation.updateStatus(Status.CANCELLED);
        Vacation savedVacation = vacationRepository.save(vacation);
        return VacationResponse.VacationDTO.from(savedVacation);
    }
}