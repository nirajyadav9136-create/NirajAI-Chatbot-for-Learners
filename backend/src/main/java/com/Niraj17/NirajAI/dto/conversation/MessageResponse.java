package com.Niraj17.NirajAI.dto.conversation;

import com.Niraj17.NirajAI.entity.MessageRole;

import java.time.Instant;
import java.util.UUID;

public record MessageResponse(UUID id, MessageRole role, String content, Instant createdAt) {
}
