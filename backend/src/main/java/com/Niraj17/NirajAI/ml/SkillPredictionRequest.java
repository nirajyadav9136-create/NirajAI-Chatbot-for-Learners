package com.Niraj17.NirajAI.ml;

import java.util.UUID;

public record SkillPredictionRequest(UUID userId, UUID topicId, int questionsAttempted, int questionsCorrect, int timeSpentMinutes) {
}
