package com.esun.library.web.controller;

import com.esun.library.app.service.BookBorrowService;
import com.esun.library.app.service.BookInventoryService;
import com.esun.library.app.service.BookReturnService;
import com.esun.library.web.dto.request.BookRequest;
import com.esun.library.web.dto.request.BorrowRequest;
import com.esun.library.web.dto.response.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    private final BookInventoryService inventoryService;
    private final BookBorrowService borrowService;
    private final BookReturnService returnService;

    @Autowired
    public BookController(BookInventoryService inventoryService,
                          BookBorrowService borrowService,
                          BookReturnService returnService) {
        this.inventoryService = inventoryService;
        this.borrowService = borrowService;
        this.returnService = returnService;
    }

    @GetMapping("/all")
    public List<InventoryResponse> allInventory() {
        return inventoryService.execute();
    }

    @PostMapping("/borrow")
    public Object borrowBook(@RequestBody BorrowRequest request) {
        try {
            borrowService.execute(request);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode().value()).body(e.getMessage());
        }
        return null;
    }

    @PostMapping("/return")
    public Object returnBook(@RequestBody BookRequest request) {
        try {
            returnService.execute(request);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode().value()).body(e.getMessage());
        }
        return null;
    }

}
