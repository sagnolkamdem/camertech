package com.microservice.camertech.mappers;

import com.microservice.camertech.dtos.UserDto;
import com.microservice.camertech.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
