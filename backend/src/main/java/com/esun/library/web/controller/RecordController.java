package com.esun.library.web.controller;

import com.esun.library.app.service.UserBorrowingRecordService;
import com.esun.library.web.dto.request.RecordRequest;
import com.esun.library.web.dto.response.RecordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowingRecord")
@CrossOrigin(origins = "http://localhost:5173")
public class RecordController {
    private final UserBorrowingRecordService recordService;

    @Autowired
    public RecordController(UserBorrowingRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/all")
    public List<RecordResponse> allRecord(@RequestBody RecordRequest request) {
        return recordService.execute(request);
    }
}
