package com.Niraj17.NirajAI.dto.auth;

import java.util.UUID;

public record UserResponse(UUID id, String email, String username, String role) {
}
