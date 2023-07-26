package com.example.server.duty;

import com.example.server.user.User;
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
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private Timestamp dutyDate;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column
    private Timestamp approvalDate;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        status = StatusType.READY;
    }
}
