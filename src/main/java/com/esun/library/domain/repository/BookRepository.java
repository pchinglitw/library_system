package com.esun.library.domain.repository;

import com.esun.library.domain.entity.Book;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, String> {
    @Procedure(name = "getAllBooks")
    Book getAllBooks();
}
