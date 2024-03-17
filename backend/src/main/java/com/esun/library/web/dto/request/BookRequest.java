package com.esun.library.web.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookRequest {
    private List<Integer> recordId;
}
