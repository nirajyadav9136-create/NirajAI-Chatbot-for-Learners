package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.topic.TopicResponse;
import com.Niraj17.NirajAI.service.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<TopicResponse> listTopics() {
        return topicService.listTopics();
    }

    @GetMapping("/{slug}")
    public TopicResponse getTopic(@PathVariable String slug) {
        return topicService.getBySlug(slug);
    }
}
