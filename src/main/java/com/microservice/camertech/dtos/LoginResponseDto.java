package com.microservice.camertech.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginResponseDto {
    private final UserDto userDto;
    private final String token;
}
