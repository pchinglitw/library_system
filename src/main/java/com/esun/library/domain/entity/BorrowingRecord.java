package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@Table(name = "borrowing_record")
public class BorrowingRecord {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "inventory_id")
    private Integer inventoryId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "inventory_id")
//    private Inventory inventory;

    @Column(nullable = false)
    private Date borrowingTime;

    private Date returnTime;
}