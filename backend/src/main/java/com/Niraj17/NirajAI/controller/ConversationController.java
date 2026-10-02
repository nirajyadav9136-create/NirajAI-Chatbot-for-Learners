package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.conversation.ConversationResponse;
import com.Niraj17.NirajAI.dto.conversation.CreateConversationRequest;
import com.Niraj17.NirajAI.dto.conversation.CreateMessageRequest;
import com.Niraj17.NirajAI.dto.conversation.MessageResponse;
import com.Niraj17.NirajAI.dto.conversation.RenameConversationRequest;
import com.Niraj17.NirajAI.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConversationResponse create(Authentication authentication, @Valid @RequestBody CreateConversationRequest request) {
        return conversationService.create(userId(authentication), request);
    }

    @GetMapping
    public List<ConversationResponse> list(Authentication authentication) {
        return conversationService.list(userId(authentication));
    }

    @GetMapping("/{conversationId}")
    public ConversationResponse get(Authentication authentication, @PathVariable UUID conversationId) {
        return conversationService.get(userId(authentication), conversationId);
    }

    @PatchMapping("/{conversationId}")
    public ConversationResponse rename(Authentication authentication, @PathVariable UUID conversationId,
                                      @Valid @RequestBody RenameConversationRequest request) {
        return conversationService.rename(userId(authentication), conversationId, request);
    }

    @PostMapping("/{conversationId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse addMessage(Authentication authentication, @PathVariable UUID conversationId,
                                      @Valid @RequestBody CreateMessageRequest request) {
        return conversationService.addUserMessage(userId(authentication), conversationId, request);
    }

    @DeleteMapping("/{conversationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable UUID conversationId) {
        conversationService.delete(userId(authentication), conversationId);
    }

    private UUID userId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
