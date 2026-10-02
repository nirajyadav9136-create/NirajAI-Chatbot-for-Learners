package com.Niraj17.NirajAI.dto.quiz;

import java.time.Instant;
import java.util.UUID;

public record QuizResultResponse(UUID attemptId, UUID quizId, int score, int totalQuestions, int correctAnswers, Instant completedAt) {
}
