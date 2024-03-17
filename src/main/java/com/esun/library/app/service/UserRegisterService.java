package com.esun.library.app.service;

import com.esun.library.app.mapper.UserMapper;
import com.esun.library.domain.entity.User;
import com.esun.library.domain.service.UserService;
import com.esun.library.web.dto.request.RegisterRequest;
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
public class UserRegisterService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserRegisterService(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public UserResponse execute(RegisterRequest request) {
        String phoneNum = request.getPhoneNum();
        String pwd = request.getPassword();
        String name = request.getName();

        if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(pwd) || StringUtils.isBlank(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "phone number, password, user name cannot be empty.");
        }

        Optional<User> userOpt = userService.findByPhoneNum(phoneNum);
        if (userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this phone number had been registered.");
        }

        User user = mapper.requestToEntity(request);
        // 雜湊密碼
        String encodedPassword = passwordEncoder.encode(pwd);
        user.setPasswordHash(encodedPassword);

        try {
            userService.register(user);
            userOpt = userService.findByPhoneNum(phoneNum);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return userOpt.map(mapper::entityToUserResponse).orElse(null);
    }
}
