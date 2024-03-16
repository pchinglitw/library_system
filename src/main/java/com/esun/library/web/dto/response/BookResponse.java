package com.esun.library.web.dto.response;

import lombok.Data;

@Data
public class BookResponse {
    private String msg;

    private String isbn;

    private String name;

    private String author;
}
