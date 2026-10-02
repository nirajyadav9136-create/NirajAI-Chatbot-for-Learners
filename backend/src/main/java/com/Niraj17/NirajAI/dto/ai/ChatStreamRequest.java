package com.Niraj17.NirajAI.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ChatStreamRequest(
        @NotNull UUID conversationId,
        @NotBlank @Size(max = 20000) String content
) {
}
