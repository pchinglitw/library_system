package com.esun.library.app.service;

import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.service.InventoryService;
import com.esun.library.web.dto.request.BorrowRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookBorrowService {
    private final InventoryService inventoryService;

    @Autowired
    public BookBorrowService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void execute(BorrowRequest request) {
        Integer userId = request.getUserId();
        List<Integer> inventoryIdList = request.getInventoryId();

        if (userId == null || CollectionUtils.isEmpty(inventoryIdList)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        inventoryIdList.forEach(id -> {
            Optional<Inventory> inventoryOpt = inventoryService.findInventoryById(id);
            if (inventoryOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "inventoryId not found");
            }

            if (!StringUtils.equals(inventoryOpt.get().getStatus().getStatusId(), "1")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "The status is currently not ready for borrowing books.");
            }
        });

        try {
            inventoryService.bookBorrow(inventoryIdList, userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
