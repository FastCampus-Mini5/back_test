package com.example.server.schedule.vacation.repository;

import com.example.server.schedule.vacation.model.VacationInfo;
import com.example.server.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacationInfoRepository extends JpaRepository<VacationInfo, Long> {
    Optional<VacationInfo> findByUser(User user);
}