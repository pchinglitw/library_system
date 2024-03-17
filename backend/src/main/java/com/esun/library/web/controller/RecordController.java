package com.esun.library.web.controller;

import com.esun.library.app.service.UserBorrowingRecordService;
import com.esun.library.web.dto.request.RecordRequest;
import com.esun.library.web.dto.response.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrowingRecord")
public class RecordController {
    private final UserBorrowingRecordService recordService;

    @Autowired
    public RecordController(UserBorrowingRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/all")
    public List<RecordResponse> allInventory(@RequestBody RecordRequest request) {
        return recordService.execute(request);
    }
}
