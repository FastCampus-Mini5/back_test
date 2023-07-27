package com.example.server.vacation.model;

import com.example.server.common.Status;
import com.example.server.user.model.User;
import com.example.server.vacation.enums.Reason;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Table(name = "vacation_tb")
@Entity
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Reason reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Timestamp startDate;

    @Column(nullable = false)
    private Timestamp endDate;

    private Timestamp approvalDate;

    @CreationTimestamp
    private Timestamp createdDate;

    @PrePersist
    protected void onCreate() {
        status = Status.PENDING;
    }
}
