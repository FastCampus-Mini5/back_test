package com.example.server.schedule.duty.service;


import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception400;
import com.example.server._core.errors.exception.Exception404;
import com.example.server.schedule.duty.dto.DutyRequest;
import com.example.server.schedule.duty.dto.DutyResponse;
import com.example.server.schedule.duty.model.Duty;
import com.example.server.schedule.duty.repository.DutyRepository;
import com.example.server.user.model.User;
import com.example.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DutyService {

    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;

    @Transactional
    public DutyResponse.DutyDTO requestDuty(DutyRequest.AddDTO dutyRequest, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404(ErrorMessage.USER_NOT_FOUND));

        Duty duty = dutyRequest.toEntityWith(user);

        Optional<Duty> existingDuty = dutyRepository.findByUserAndDutyDate(duty.getUser(), duty.getDutyDate());
        existingDuty.ifPresent(existing -> {
            throw new Exception400(ErrorMessage.DUTY_ALREADY_EXISTS);
        });

        Duty savedDuty = dutyRepository.save(duty);
        return DutyResponse.DutyDTO.from(savedDuty);
    }
}