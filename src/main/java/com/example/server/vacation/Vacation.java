package com.example.server.vacation;

import com.example.server.user.User;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Reason reason;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Timestamp startDate;

    @UpdateTimestamp
    private Timestamp endDate;

    @CreationTimestamp
    private Timestamp approvalDate;

    @UpdateTimestamp
    private Timestamp createdDate;

    @PrePersist
    protected void onCreate() {
        status = Status.READY;
    }
}
