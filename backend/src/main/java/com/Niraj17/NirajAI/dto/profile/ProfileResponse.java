package com.Niraj17.NirajAI.dto.profile;

import com.Niraj17.NirajAI.entity.ExperienceLevel;
import com.Niraj17.NirajAI.entity.LearningGoal;

import java.util.UUID;

public record ProfileResponse(
        UUID userId,
        String displayName,
        String bio,
        String profileImageUrl,
        String preferredLanguage,
        ExperienceLevel experienceLevel,
        LearningGoal learningGoal
) {
}
