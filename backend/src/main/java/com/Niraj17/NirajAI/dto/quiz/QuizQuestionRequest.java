package com.Niraj17.NirajAI.dto.quiz;

import com.Niraj17.NirajAI.entity.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuizQuestionRequest(
        @NotNull QuestionType questionType,
        @NotBlank @Size(max = 4000) String question,
        @Size(max = 4000) String options,
        @NotBlank @Size(max = 1000) String correctAnswer,
        @NotBlank @Size(max = 4000) String explanation
) {
}
