package com.esun.library.domain.service;

import com.esun.library.domain.entity.Book;
import com.esun.library.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Book> allBook() {
//        System.out.println(bookRepository.getAllBooks());
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getAllBooks", Book.class);
        query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        query.execute();

        List<Book> bookList = query.getResultList();
        System.out.println(bookList);
        bookList.forEach(book -> {
            System.out.println(book.getIsbn());
        });

        return bookList;
    }
}
