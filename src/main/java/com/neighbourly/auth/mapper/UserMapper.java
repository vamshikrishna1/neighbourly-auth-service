package com.neighbourly.auth.mapper;

import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(RegisterRequest request);

}
