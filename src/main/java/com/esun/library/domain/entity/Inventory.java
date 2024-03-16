package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @Column(name = "status_id", nullable = false)
    private String statusId;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Date storeTime;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", insertable=false, updatable=false)
    private BookStatus status;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", insertable=false, updatable=false)
    private Book book;
//
//    @OneToMany(mappedBy = "inventory")
//    private List<BorrowingRecord> borrowingRecords;
}
