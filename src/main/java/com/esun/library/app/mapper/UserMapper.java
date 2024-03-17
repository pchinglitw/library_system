package com.esun.library.app.mapper;

import com.esun.library.domain.entity.User;
import com.esun.library.web.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "phoneNum", target = "phoneNumber")
    @Mapping(source = "password", target = "passwordHash")
    @Mapping(source = "name", target = "userName")
    User requestToEntity(RegisterRequest request);
}
