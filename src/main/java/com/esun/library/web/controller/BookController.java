package com.esun.library.web.controller;

import com.esun.library.app.service.BookBorrowService;
import com.esun.library.app.service.BookInventoryService;
import com.esun.library.web.dto.request.BookRequest;
import com.esun.library.web.dto.response.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookInventoryService inventoryService;
    private final BookBorrowService borrowService;

    @Autowired
    public BookController(BookInventoryService inventoryService, BookBorrowService borrowService) {
        this.inventoryService = inventoryService;
        this.borrowService = borrowService;
    }

    @GetMapping("/all")
    public List<InventoryResponse> allInventory() {
        return inventoryService.execute();
    }

    @PostMapping("/borrow")
    public Object borrowBook(@RequestBody BookRequest request) {
        borrowService.execute(request);
        return null;
    }

}
