package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.auth.AuthResponse;
import com.Niraj17.NirajAI.dto.auth.ChangePasswordRequest;
import com.Niraj17.NirajAI.dto.auth.LoginRequest;
import com.Niraj17.NirajAI.dto.auth.RefreshRequest;
import com.Niraj17.NirajAI.dto.auth.RegisterRequest;
import com.Niraj17.NirajAI.dto.auth.UserResponse;
import com.Niraj17.NirajAI.entity.RefreshToken;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.repository.RefreshTokenRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import com.Niraj17.NirajAI.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();
        if (userRepository.existsByEmailIgnoreCase(email) || userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email or username is already registered");
        }
        User user = userRepository.save(new User(email, username, passwordEncoder.encode(request.password())));
        return issueTokens(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email().trim())
                .filter(candidate -> candidate.isEnabled() && passwordEncoder.matches(request.password(), candidate.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        return issueTokens(user);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        String rawToken = request.refreshToken();
        User user = refreshTokenRepository.findByTokenHash(hash(rawToken))
                .filter(token -> !token.isRevoked() && token.getExpiresAt().isAfter(Instant.now()))
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
        try {
            jwtService.getUserId(rawToken);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token", exception);
        }
        return new AuthResponse(jwtService.createAccessToken(user), rawToken, "Bearer", user.getId(), user.getUsername(), user.getRole().name());
    }

    @Transactional
    public void logout(RefreshRequest request) {
        refreshTokenRepository.findByTokenHash(hash(request.refreshToken())).ifPresent(token -> {
            token.revoke();
            refreshTokenRepository.save(token);
        });
    }

    @Transactional(readOnly = true)
    public UserResponse currentUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getRole().name());
    }

    @Transactional
    public void changePassword(UUID userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }
        user.changePasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    private AuthResponse issueTokens(User user) {
        String tokenId = UUID.randomUUID().toString();
        String refreshToken = jwtService.createRefreshToken(user, tokenId);
        refreshTokenRepository.save(new RefreshToken(user, hash(refreshToken), jwtService.getExpiration(refreshToken)));
        return new AuthResponse(jwtService.createAccessToken(user), refreshToken, "Bearer", user.getId(), user.getUsername(), user.getRole().name());
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }
}
