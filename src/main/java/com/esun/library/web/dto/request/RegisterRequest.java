package com.esun.library.web.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String phoneNum;

    private String password;

    private String name;

}
