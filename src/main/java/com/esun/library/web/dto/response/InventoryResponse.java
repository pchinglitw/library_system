package com.esun.library.web.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryResponse {
    private Integer inventoryId;

    private Date storeTime;

    private String status;

    private String isbn;

    private String name;

    private String author;

    private String introduction;
}
