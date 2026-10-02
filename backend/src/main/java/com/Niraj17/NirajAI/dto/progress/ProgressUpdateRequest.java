package com.Niraj17.NirajAI.dto.progress;

import jakarta.validation.constraints.Min;

public record ProgressUpdateRequest(
        @Min(0) int questionsAttempted,
        @Min(0) int questionsCorrect,
        @Min(0) int timeSpentMinutes
) {
}
