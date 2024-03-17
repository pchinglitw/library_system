package com.esun.library.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RecordResponse {
    private Integer recordId;

    private Integer inventoryId;

    private String isbn;

    private String bookName;

    private String author;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date borrowingTime;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date returnTime;

    private String status;
}
