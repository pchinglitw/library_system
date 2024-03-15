package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Date registrationTime;

    private Date lastLoginTime;

    @OneToMany(mappedBy = "user")
    private List<BorrowingRecord> borrowingRecords;
}
