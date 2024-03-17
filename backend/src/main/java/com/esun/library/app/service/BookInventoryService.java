package com.esun.library.app.service;

import com.esun.library.app.mapper.InventoryMapper;
import com.esun.library.domain.entity.Book;
import com.esun.library.domain.entity.BookStatus;
import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.service.BookService;
import com.esun.library.domain.service.BookStatusService;
import com.esun.library.domain.service.InventoryService;
import com.esun.library.web.dto.response.InventoryResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookInventoryService {
    private final InventoryService inventoryService;
    private final BookService bookService;
    private final BookStatusService statusService;

    private List<BookStatus> statusList;
    private final InventoryMapper mapper = Mappers.getMapper(InventoryMapper.class);

    @Autowired
    public BookInventoryService(InventoryService inventoryService,
                                BookService bookService,
                                BookStatusService statusService) {
        this.inventoryService = inventoryService;
        this.bookService = bookService;
        this.statusService = statusService;
    }

    public List<InventoryResponse> execute() {
        List<InventoryResponse> responseList = new ArrayList<>();

        // get all entity in Inventory
        List<Inventory> inventoryList = inventoryService.findAllInventory();

        // get all entity in Book for mapping response
        List<Book> bookList = bookService.findAllBook();

        // get all entity in Book_Status for mapping response.status
        statusList = statusService.findAllStatusName();

        inventoryList.forEach(inventory -> bookList.stream()
                .filter(book -> StringUtils.equals(inventory.getBook().getIsbn(), book.getIsbn()))
                .forEach(book -> {
                    InventoryResponse response = mapper.entityToInventoryResponse(inventory);
                    response.setStatus(getStausName(inventory.getStatus().getStatusId()));
                    response.setAuthor(book.getAuthor());
                    response.setName(book.getName());
                    response.setAuthor(book.getAuthor());
                    response.setIntroduction(book.getIntroduction());

                    responseList.add(response);
                }));

        return responseList.stream().sorted(Comparator.comparing(InventoryResponse::getInventoryId)).toList();
    }

    private String getStausName(String statusId) {
        Optional<BookStatus> bookStatusOpt = statusList.stream().filter(status -> StringUtils.equals(status.getStatusId(), statusId)).findAny();
        if (bookStatusOpt.isPresent()) {
            return bookStatusOpt.get().getStatusName();
        }

        return "";
    }

}
