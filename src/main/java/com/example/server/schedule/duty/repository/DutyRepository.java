package com.example.server.schedule.duty.repository;


import com.example.server.schedule.duty.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyRepository extends JpaRepository<Duty, Long> {
}

