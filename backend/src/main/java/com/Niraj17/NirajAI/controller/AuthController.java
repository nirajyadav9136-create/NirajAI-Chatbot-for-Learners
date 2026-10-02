package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.auth.AuthResponse;
import com.Niraj17.NirajAI.dto.auth.ChangePasswordRequest;
import com.Niraj17.NirajAI.dto.auth.LoginRequest;
import com.Niraj17.NirajAI.dto.auth.RefreshRequest;
import com.Niraj17.NirajAI.dto.auth.RegisterRequest;
import com.Niraj17.NirajAI.dto.auth.UserResponse;
import com.Niraj17.NirajAI.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshRequest request) {
        authService.logout(request);
    }

    @GetMapping("/me")
    public UserResponse currentUser(Authentication authentication) {
        return authService.currentUser(UUID.fromString(authentication.getName()));
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(UUID.fromString(authentication.getName()), request);
    }
}
