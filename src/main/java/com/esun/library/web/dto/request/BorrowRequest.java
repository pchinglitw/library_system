package com.esun.library.web.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BorrowRequest {

    private Integer userId;

    private List<Integer> inventoryId;

}
