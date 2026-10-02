package com.Niraj17.NirajAI.dto.recommendation;

import java.util.UUID;

public record RecommendationResponse(UUID topicId, String topicName, String topicSlug, String reason, int priority) {
}
