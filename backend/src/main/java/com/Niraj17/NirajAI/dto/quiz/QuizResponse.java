package com.Niraj17.NirajAI.dto.quiz;

import java.util.List;
import java.util.UUID;

public record QuizResponse(UUID id, UUID topicId, String title, String difficulty, List<QuizQuestionResponse> questions) {
}
