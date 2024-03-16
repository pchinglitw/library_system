package com.esun.library.app.service;

import com.esun.library.app.mapper.BorrowingRecordMapper;
import com.esun.library.domain.entity.Book;
import com.esun.library.domain.entity.BorrowingRecord;
import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.service.BookService;
import com.esun.library.domain.service.BorrowingRecordService;
import com.esun.library.domain.service.InventoryService;
import com.esun.library.web.dto.request.RecordRequest;
import com.esun.library.web.dto.response.RecordResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserBorrowingRecordService {
    private final BorrowingRecordService recordService;
    private final BookService bookService;
    private final InventoryService inventoryService;
    private final BorrowingRecordMapper mapper = Mappers.getMapper(BorrowingRecordMapper.class);
    private List<Book> bookList;
    private List<Inventory> inventoryList;

    @Autowired
    public UserBorrowingRecordService(BorrowingRecordService recordService,
                                      BookService bookService,
                                      InventoryService inventoryService) {
        this.recordService = recordService;
        this.bookService = bookService;
        this.inventoryService = inventoryService;
    }

    public List<RecordResponse> execute(RecordRequest request) {
        Integer userId = request.getUserId();
        System.out.println(userId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<RecordResponse> responseList = new ArrayList<>();

        List<BorrowingRecord> recordList = recordService.findRecordByUserId(userId);

        bookList = bookService.findAllBook();
        inventoryList = inventoryService.findAllInventory();

        recordList.forEach(borrowingRecord -> {
            RecordResponse response = mapper.entityToInventoryResponse(borrowingRecord);
            setResponseByIsbn(response, findIsbn(borrowingRecord.getInventoryId()));

            responseList.add(response);
        });

        return responseList;
    }

    private String findIsbn(Integer inventoryId) {
        Optional<Inventory> inventoryOpt = inventoryList.stream().filter(inventory -> Objects.equals(inventory.getInventoryId(), inventoryId)).findAny();
        return inventoryOpt.map(Inventory::getIsbn).orElse(null);
    }

    private void setResponseByIsbn(RecordResponse response, String isbn) {
        response.setIsbn(isbn);
        bookList.stream().filter(book -> StringUtils.equals(book.getIsbn(), isbn)).forEach(book -> {
            response.setBookName(book.getName());
            response.setAuthor(book.getAuthor());
        });
    }

}
