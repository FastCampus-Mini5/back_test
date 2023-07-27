package com.example.server.duty.model;


import com.example.server.common.Status;
import com.example.server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "on_duty_tb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Duty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Timestamp dutyDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Timestamp approvalDate;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;

    @PrePersist
    protected void onCreate() {
        status = Status.PENDING;
    }
}
