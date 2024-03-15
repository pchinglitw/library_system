package com.esun.library.web.controller;

import com.esun.library.domain.entity.Book;
import com.esun.library.domain.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<Book> allBook() {
        System.out.println(bookService.allBook());
        return bookService.allBook();
    }
//
//    @PostMapping("/borrow")
//    public AnnouncementPo borrowBook(@RequestBody AnnouncementPo announcementPo) {
//        announcementService.borrowBook(announcementPo);
//        return announcementPo;
//    }

}
