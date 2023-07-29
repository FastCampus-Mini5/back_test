package com.example.server.schedule.duty.service;


import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception400;
import com.example.server.schedule.duty.model.Duty;
import com.example.server.schedule.duty.repository.DutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DutyService {
    private final DutyRepository dutyRepository;

    @Transactional
    public Duty requestDuty(Duty dutyRequest) {
        Optional<Duty> existingDuty = dutyRepository.findByUserAndDutyDate(dutyRequest.getUser(), dutyRequest.getDutyDate());
        if (existingDuty.isPresent()) {
            throw new Exception400(ErrorMessage.DUTY_ALREADY_EXISTS);
        }

        return dutyRepository.save(dutyRequest);
    }
}

