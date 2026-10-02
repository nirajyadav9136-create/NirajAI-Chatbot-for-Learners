package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.recommendation.RecommendationResponse;
import com.Niraj17.NirajAI.service.RecommendationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<RecommendationResponse> recommend(Authentication authentication) {
        return recommendationService.recommend(UUID.fromString(authentication.getName()));
    }
}
