package com.microservice.camertech.services;

import com.microservice.camertech.dtos.LoginDto;
import com.microservice.camertech.dtos.LoginResponseDto;
import com.microservice.camertech.dtos.RegisterDto;
import com.microservice.camertech.dtos.UserDto;
import com.microservice.camertech.entities.User;
import com.microservice.camertech.mappers.UserMapper;
import com.microservice.camertech.repositories.UserRepository;
import com.microservice.camertech.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        final User user = User.builder()
                .username(registerDto.getUsername())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        final User userSaved = userRepository.save(user);
        return userMapper.toDto(userSaved);
}

    public LoginResponseDto login(final LoginDto loginDto) {
        final User user = userRepository.findByUsername(loginDto.getUsername());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        final String token = jwtProvider.generateToken(user.getId(), user.getEmail());

        return LoginResponseDto.builder()
                .userDto(userMapper.toDto(user))
                .token(token)
                .build();
    }
}
