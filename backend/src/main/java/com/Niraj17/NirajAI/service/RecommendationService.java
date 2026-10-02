package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.recommendation.RecommendationResponse;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.TopicProgress;
import com.Niraj17.NirajAI.ml.MLServiceClient;
import com.Niraj17.NirajAI.ml.SkillPredictionRequest;
import com.Niraj17.NirajAI.repository.TopicProgressRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final TopicRepository topicRepository;
    private final TopicProgressRepository progressRepository;
    private final MLServiceClient mlServiceClient;

    public RecommendationService(TopicRepository topicRepository, TopicProgressRepository progressRepository,
                                 MLServiceClient mlServiceClient) {
        this.topicRepository = topicRepository;
        this.progressRepository = progressRepository;
        this.mlServiceClient = mlServiceClient;
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponse> recommend(UUID userId) {
        Map<UUID, TopicProgress> progressByTopic = progressRepository.findAllByUserIdOrderByLastStudiedAtDesc(userId).stream()
                .collect(Collectors.toMap(progress -> progress.getTopic().getId(), Function.identity()));
        return topicRepository.findAllByOrderByCategoryAscNameAsc().stream()
            .map(topic -> recommendation(topic, progressByTopic.get(topic.getId()), userId))
                .sorted((left, right) -> Integer.compare(left.priority(), right.priority()))
                .limit(5)
                .toList();
    }

    private RecommendationResponse recommendation(Topic topic, TopicProgress progress, UUID userId) {
        if (progress == null) {
            return new RecommendationResponse(topic.getId(), topic.getName(), topic.getSlug(),
                    "Start with the fundamentals of this topic.", 1);
        }
        int skillScore = mlServiceClient.predictSkill(new SkillPredictionRequest(
                userId,
                topic.getId(),
                progress.getQuestionsAttempted(),
                progress.getQuestionsCorrect(),
                progress.getTimeSpentMinutes()))
            .map(prediction -> Math.round(prediction.skillScore()))
            .orElse(progress.getSkillScore());
        if (skillScore < 50) {
            return new RecommendationResponse(topic.getId(), topic.getName(), topic.getSlug(),
                    "Review the fundamentals and complete more practice questions.", 1);
        }
        return new RecommendationResponse(topic.getId(), topic.getName(), topic.getSlug(),
                "Continue practicing to strengthen this skill.", 2);
    }
}
