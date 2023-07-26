package com.example.server.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "sign_up_tb")
@Entity
public class SignUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Timestamp hireDate;

    @CreationTimestamp
    private Timestamp createdDate;
}
