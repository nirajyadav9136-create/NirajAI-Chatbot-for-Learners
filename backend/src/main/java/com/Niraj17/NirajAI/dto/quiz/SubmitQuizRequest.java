package com.Niraj17.NirajAI.dto.quiz;

import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record SubmitQuizRequest(@NotNull Map<UUID, String> answers) {
}
