package com.esun.library.web.controller;

import com.esun.library.app.service.UserLoginService;
import com.esun.library.app.service.UserRegisterService;
import com.esun.library.web.dto.request.LoginRequest;
import com.esun.library.web.dto.request.RegisterRequest;
import com.esun.library.web.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRegisterService registerService;
    private final UserLoginService loginService;

    @Autowired
    public UserController(UserRegisterService registerService, UserLoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest request) {
        try {
            registerService.execute(request);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode().value()).body(e.getMessage());
        }
        return null;
    }

    @PostMapping("/login")
    public Object register(@RequestBody LoginRequest request) {
        UserResponse response = new UserResponse();

        try {
            response = loginService.execute(request);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode().value()).body(e.getMessage());
        }

        return response;
    }

}
