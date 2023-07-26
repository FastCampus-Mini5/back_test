package com.example.server.vacation;

import com.example.server.user.model.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "vacation_info_tb")
@Entity
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
}
