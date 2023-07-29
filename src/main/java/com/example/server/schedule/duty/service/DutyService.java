package com.example.server.schedule.duty.service;


import com.example.server.schedule.duty.model.Duty;
import com.example.server.schedule.duty.repository.DutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DutyService {
    private final DutyRepository dutyRepository;

    @Transactional
    public Duty requestDuty(Duty dutyRequest) {
        return dutyRepository.save(dutyRequest);
    }
}

