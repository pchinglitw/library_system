package com.esun.library.app.service;

import com.esun.library.domain.entity.BorrowingRecord;
import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.service.BorrowingRecordService;
import com.esun.library.domain.service.InventoryService;
import com.esun.library.web.dto.request.BookRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookReturnService {
    private final InventoryService inventoryService;
    private final BorrowingRecordService recordService;

    @Autowired
    public BookReturnService(InventoryService inventoryService, BorrowingRecordService recordService) {
        this.inventoryService = inventoryService;
        this.recordService = recordService;
    }

    public void execute(BookRequest request) {
        Integer inventoryId = request.getInventoryId();
        Integer recordId = request.getRecordId();

        if (inventoryId == null || recordId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Inventory> inventoryOpt = inventoryService.findInventoryById(inventoryId);
        if (inventoryOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "inventoryId not found");
        }

        Optional<BorrowingRecord> recordOpt = recordService.findRecordById(recordId);
        if (recordOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "recordId not found");
        }

        if (StringUtils.equals(inventoryOpt.get().getStatus().getStatusId(), "1")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The status is currently not ready for returning books.");
        }

        try {
            inventoryService.bookReturn(inventoryId, recordId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
