package com.Niraj17.NirajAI.dto.conversation;

import com.Niraj17.NirajAI.entity.LearningMode;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ConversationResponse(
        UUID id,
        String title,
        String model,
        LearningMode learningMode,
        UUID topicId,
        Instant createdAt,
        Instant updatedAt,
        List<MessageResponse> messages
) {
}
