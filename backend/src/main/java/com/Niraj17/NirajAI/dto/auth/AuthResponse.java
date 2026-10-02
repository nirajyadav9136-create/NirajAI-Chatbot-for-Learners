package com.Niraj17.NirajAI.dto.auth;

import java.util.UUID;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        UUID userId,
        String username,
        String role
) {
}
