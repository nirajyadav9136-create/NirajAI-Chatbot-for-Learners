package com.Niraj17.NirajAI.dto.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateQuizRequest(
        @NotNull UUID topicId,
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 20) String difficulty,
        @NotEmpty @Valid List<QuizQuestionRequest> questions
) {
}
