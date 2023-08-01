package com.example.server.schedule.vacation.model;

import com.example.server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vacation_info_tb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VacationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int remainVacation;

    @Column(nullable = false)
    private int usedVacation;

    @PrePersist
    public void onCreate() {
        remainVacation = 0;
        usedVacation = 0;
    }

    public void updateInfo(int vacationDays) {
        this.remainVacation -= vacationDays;
        this.usedVacation += vacationDays;
    }
}