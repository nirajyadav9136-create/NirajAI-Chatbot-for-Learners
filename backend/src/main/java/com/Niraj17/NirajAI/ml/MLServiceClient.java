package com.Niraj17.NirajAI.ml;

import java.util.Optional;

public interface MLServiceClient {

    Optional<SkillPredictionResponse> predictSkill(SkillPredictionRequest request);

    boolean isAvailable();
}
