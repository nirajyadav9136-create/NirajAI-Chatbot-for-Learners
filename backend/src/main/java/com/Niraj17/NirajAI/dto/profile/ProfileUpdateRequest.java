package com.Niraj17.NirajAI.dto.profile;

import com.Niraj17.NirajAI.entity.ExperienceLevel;
import com.Niraj17.NirajAI.entity.LearningGoal;
import jakarta.validation.constraints.Size;

public record ProfileUpdateRequest(
        @Size(max = 100) String displayName,
        @Size(max = 1000) String bio,
        @Size(max = 500) String profileImageUrl,
        @Size(max = 50) String preferredLanguage,
        ExperienceLevel experienceLevel,
        LearningGoal learningGoal
) {
}
