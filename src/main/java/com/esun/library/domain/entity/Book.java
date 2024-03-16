package com.esun.library.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "book")
public class Book {
    @Id
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String introduction;
//
//    @OneToMany(mappedBy = "book")
//    private List<Inventory> inventories;
}
