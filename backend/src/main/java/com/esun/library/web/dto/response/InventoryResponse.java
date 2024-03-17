package com.esun.library.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InventoryResponse {
    private Integer inventoryId;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date storeTime;

    private String status;

    private String isbn;

    private String name;

    private String author;

    private String introduction;
}
