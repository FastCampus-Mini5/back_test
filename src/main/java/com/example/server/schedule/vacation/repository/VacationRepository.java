package com.example.server.schedule.vacation.repository;


import com.example.server.schedule.vacation.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

}