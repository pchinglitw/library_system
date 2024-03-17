package com.esun.library.web.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String phoneNum;

    private String password;

}
