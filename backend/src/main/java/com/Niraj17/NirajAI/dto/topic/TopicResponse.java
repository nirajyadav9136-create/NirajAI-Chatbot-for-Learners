package com.Niraj17.NirajAI.dto.topic;

import com.Niraj17.NirajAI.entity.TopicCategory;
import com.Niraj17.NirajAI.entity.TopicDifficulty;

import java.util.UUID;

public record TopicResponse(
        UUID id,
        String name,
        String slug,
        String description,
        TopicCategory category,
        TopicDifficulty difficulty
) {
}
