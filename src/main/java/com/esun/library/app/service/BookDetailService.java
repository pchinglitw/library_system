package com.esun.library.app.service;

import com.esun.library.domain.entity.Book;
import com.esun.library.domain.service.BookService;
import com.esun.library.web.dto.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDetailService {
    @Autowired
    private BookService bookService;

    public BookResponse execute() {
        List<Book> bookList = bookService.findAllBook();

        return null;
    }
}
