package com.esun.library.app.service;

import com.esun.library.app.mapper.UserMapper;
import com.esun.library.domain.entity.User;
import com.esun.library.domain.service.UserService;
import com.esun.library.web.dto.request.LoginRequest;
import com.esun.library.web.dto.response.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserLoginService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserLoginService(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public UserResponse execute(LoginRequest request) {
        String phoneNum = request.getPhoneNum();
        String pwd = request.getPassword();

        if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(pwd)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "phone number and password cannot be empty.");
        }

        Optional<User> userOpt = userService.findByPhoneNum(phoneNum);
        if (userOpt.isEmpty() || !passwordEncoder.matches(pwd, userOpt.get().getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid phone number or password.");
        }

        return mapper.entityToUserResponse(userOpt.get());
    }
}
