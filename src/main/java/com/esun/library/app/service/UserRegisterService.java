package com.esun.library.app.service;

import com.esun.library.app.mapper.UserMapper;
import com.esun.library.domain.entity.User;
import com.esun.library.domain.service.UserService;
import com.esun.library.web.dto.request.RegisterRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserRegisterService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public void execute(RegisterRequest request) {
        System.out.println(request.getPassword());

        Optional<User> userOpt = userService.findByPhoneNum(request.getPhoneNum());
        System.out.println(userOpt);
        if (userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this phone number had been registered");
        }

        User user = mapper.requestToEntity(request);
        // 雜湊密碼
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(encodedPassword);
        System.out.println(user);

        try {
            userService.register(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
