package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "book_status")
public class BookStatus {
    @Id
    @Column(name = "status_id")
    private String statusId;

    @Column(nullable = false)
    private String statusName;
}
