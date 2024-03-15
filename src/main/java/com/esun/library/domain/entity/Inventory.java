package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Date storeTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", insertable = false, updatable = false)
    private Book book;

    @OneToMany(mappedBy = "inventory")
    private List<BorrowingRecord> borrowingRecords;
}
