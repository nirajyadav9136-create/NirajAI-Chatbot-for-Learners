package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.ai.ChatStreamRequest;
import com.Niraj17.NirajAI.service.AIChatService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AIChatService chatService;

    public ChatController(AIChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(Authentication authentication, @Valid @RequestBody ChatStreamRequest request) {
        return chatService.stream(UUID.fromString(authentication.getName()), request);
    }
}
