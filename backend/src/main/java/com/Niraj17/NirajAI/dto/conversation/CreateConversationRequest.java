package com.Niraj17.NirajAI.dto.conversation;

import com.Niraj17.NirajAI.entity.LearningMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateConversationRequest(
        @NotBlank @Size(max = 200) String title,
        LearningMode learningMode,
        UUID topicId,
        @Size(max = 100) String model
) {
}
