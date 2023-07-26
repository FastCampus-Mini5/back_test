package com.example.server.vacation;

import com.example.server.user.User;
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

    private int remainVacation;
    private int usedVacation;
}
