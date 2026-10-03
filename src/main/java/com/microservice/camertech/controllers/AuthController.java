package com.microservice.camertech.controllers;

import com.microservice.camertech.dtos.LoginDto;
import com.microservice.camertech.dtos.LoginResponseDto;
import com.microservice.camertech.dtos.RegisterDto;
import com.microservice.camertech.dtos.UserDto;
import com.microservice.camertech.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto registerDto) {
        log.info("Registering user");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("Logging in user");
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        log.info("Logging out user");
        // Here you would typically handle the logout logic, such as invalidating the user's session or token
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestBody String token) {
        log.info("Verifying token: {}", token);
        // Here you would typically verify the provided token
        return ResponseEntity.ok("Token is valid");
    }

    @PostMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        log.info("Fetching current user information");
        // Here you would typically return the currently authenticated user's information
        return ResponseEntity.ok(new UserDto());
    }
}
