package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.progress.ProgressResponse;
import com.Niraj17.NirajAI.dto.progress.ProgressUpdateRequest;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.TopicProgress;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.repository.TopicProgressRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProgressService {

    private final TopicProgressRepository progressRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ProgressService(TopicProgressRepository progressRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.progressRepository = progressRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<ProgressResponse> listProgress(UUID userId) {
        return progressRepository.findAllByUserIdOrderByLastStudiedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProgressResponse getProgress(UUID userId, UUID topicId) {
        return progressRepository.findByUserIdAndTopicId(userId, topicId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found"));
    }

    @Transactional
    public ProgressResponse recordProgress(UUID userId, UUID topicId, ProgressUpdateRequest request) {
        if (request.questionsCorrect() > request.questionsAttempted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correct answers cannot exceed attempts");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        TopicProgress progress = progressRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseGet(() -> new TopicProgress(user, topic));
        progress.recordActivity(request.questionsAttempted(), request.questionsCorrect(), request.timeSpentMinutes());
        return toResponse(progressRepository.save(progress));
    }

    private ProgressResponse toResponse(TopicProgress progress) {
        return new ProgressResponse(
                progress.getTopic().getId(),
                progress.getTopic().getName(),
                progress.getTopic().getSlug(),
                progress.getSkillScore(),
                progress.getQuestionsAttempted(),
                progress.getQuestionsCorrect(),
                progress.getTimeSpentMinutes(),
                progress.getLastStudiedAt());
    }
}
