package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.entity.ActivityType;
import com.Niraj17.NirajAI.entity.MLPrediction;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.entity.UserActivity;
import com.Niraj17.NirajAI.repository.MLPredictionRepository;
import com.Niraj17.NirajAI.repository.UserActivityRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class LearningActivityService {

    private final UserActivityRepository activityRepository;
    private final MLPredictionRepository predictionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public LearningActivityService(UserActivityRepository activityRepository, MLPredictionRepository predictionRepository,
                                   UserRepository userRepository, TopicRepository topicRepository) {
        this.activityRepository = activityRepository;
        this.predictionRepository = predictionRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public void record(UUID userId, UUID topicId, ActivityType activityType, int minutes,
                       int attempted, int correct, Integer score) {
        User user = userRepository.findById(userId).orElseThrow(() -> notFound("User not found"));
        Topic topic = topicId == null ? null : topicRepository.findById(topicId).orElseThrow(() -> notFound("Topic not found"));
        activityRepository.save(new UserActivity(user, topic, activityType, minutes, attempted, correct, score));
    }

    @Transactional
    public void recordPrediction(UUID userId, UUID topicId, String type, String result, String modelVersion) {
        User user = userRepository.findById(userId).orElseThrow(() -> notFound("User not found"));
        Topic topic = topicId == null ? null : topicRepository.findById(topicId).orElseThrow(() -> notFound("Topic not found"));
        predictionRepository.save(new MLPrediction(user, topic, type, result, modelVersion));
    }

    private ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
