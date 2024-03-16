package com.esun.library.app.service;

import com.esun.library.domain.entity.Book;
import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.service.BookService;
import com.esun.library.domain.service.InventoryService;
import com.esun.library.web.dto.request.BookRequest;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookBorrowService {
    @Autowired
    private BookService bookService;
    @Autowired
    private InventoryService inventoryService;

    public void execute(BookRequest request) {
        Integer userId = request.getUserId();
        Integer inventoryId = request.getInventoryId();

        if (userId == null || inventoryId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Inventory> inventoryOpt = inventoryService.findInventoryById(inventoryId);
        if (inventoryOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "inventoryId not found");
        }

        Inventory inventory = inventoryOpt.get();

        if (!StringUtils.equals(inventory.getStatus().getStatusId(), "1")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The status is currently not ready for borrowing books.");
        }

        try {
            inventoryService.bookBorrow(inventoryId, request.getUserId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
