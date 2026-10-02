package com.Niraj17.NirajAI.dto.conversation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenameConversationRequest(@NotBlank @Size(max = 200) String title) {
}
