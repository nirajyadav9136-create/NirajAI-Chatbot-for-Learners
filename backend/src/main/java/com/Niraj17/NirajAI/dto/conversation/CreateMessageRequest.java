package com.Niraj17.NirajAI.dto.conversation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMessageRequest(@NotBlank @Size(max = 20000) String content) {
}
