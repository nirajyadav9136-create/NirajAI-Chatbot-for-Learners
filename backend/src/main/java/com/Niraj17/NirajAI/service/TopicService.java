package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.topic.TopicResponse;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional(readOnly = true)
    public List<TopicResponse> listTopics() {
        return topicRepository.findAllByOrderByCategoryAscNameAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TopicResponse getBySlug(String slug) {
        return topicRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
    }

    private TopicResponse toResponse(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getName(),
                topic.getSlug(),
                topic.getDescription(),
                topic.getCategory(),
                topic.getDifficulty());
    }
}
