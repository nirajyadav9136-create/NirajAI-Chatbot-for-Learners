package com.Niraj17.NirajAI.dto.progress;

import java.time.Instant;
import java.util.UUID;

public record ProgressResponse(
        UUID topicId,
        String topicName,
        String topicSlug,
        int skillScore,
        int questionsAttempted,
        int questionsCorrect,
        int timeSpentMinutes,
        Instant lastStudiedAt
) {
}
