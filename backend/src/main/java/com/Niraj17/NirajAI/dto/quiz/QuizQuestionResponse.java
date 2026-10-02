package com.Niraj17.NirajAI.dto.quiz;

import com.Niraj17.NirajAI.entity.QuestionType;

import java.util.UUID;

public record QuizQuestionResponse(UUID id, QuestionType questionType, String question, String options) {
}
