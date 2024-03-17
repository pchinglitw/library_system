package com.esun.library.web.controller;

import com.esun.library.app.service.UserRegisterService;
import com.esun.library.web.dto.request.RegisterRequest;
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
    private UserRegisterService registerService;

    @Autowired
    public UserController(UserRegisterService registerService) {
        this.registerService = registerService;
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

}
