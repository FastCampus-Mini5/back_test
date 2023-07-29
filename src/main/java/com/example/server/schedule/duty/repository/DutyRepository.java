package com.example.server.schedule.duty.repository;


import com.example.server.schedule.duty.model.Duty;
import com.example.server.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface DutyRepository extends JpaRepository<Duty, Long> {
    Optional<Duty> findByUserAndDutyDate(User user, Timestamp dutyDate);
}

