package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.ai.AIProvider;
import com.Niraj17.NirajAI.ai.AIPrompt;
import com.Niraj17.NirajAI.ai.TutorPromptService;
import com.Niraj17.NirajAI.dto.ai.ChatStreamRequest;
import com.Niraj17.NirajAI.dto.conversation.CreateMessageRequest;
import com.Niraj17.NirajAI.entity.Conversation;
import com.Niraj17.NirajAI.observability.ChatMetrics;
import com.Niraj17.NirajAI.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AIChatService {

    private final ConversationService conversationService;
    private final UserProfileRepository profileRepository;
    private final TutorPromptService promptService;
    private final RAGService ragService;
    private final AIProvider aiProvider;
    private final ChatMetrics chatMetrics;

    public AIChatService(ConversationService conversationService, UserProfileRepository profileRepository,
                         TutorPromptService promptService, RAGService ragService, AIProvider aiProvider,
                         ChatMetrics chatMetrics) {
        this.conversationService = conversationService;
        this.profileRepository = profileRepository;
        this.promptService = promptService;
        this.ragService = ragService;
        this.aiProvider = aiProvider;
        this.chatMetrics = chatMetrics;
    }

    public SseEmitter stream(UUID userId, ChatStreamRequest request) {
        chatMetrics.requested();
        SseEmitter emitter = new SseEmitter(120_000L);
        CompletableFuture.runAsync(() -> generate(userId, request, emitter));
        return emitter;
    }

    private void generate(UUID userId, ChatStreamRequest request, SseEmitter emitter) {
        try {
            Conversation conversation = conversationService.findOwnedConversation(userId, request.conversationId());
            conversationService.addUserMessage(userId, request.conversationId(), new CreateMessageRequest(request.content()));
            var profile = profileRepository.findByUserId(userId).orElse(null);
            AIPrompt prompt = promptService.build(conversation, profile, request.content(), ragService.referenceContext(request.content()));

            send(emitter, "start", "Generating a tutor response");
            String response = aiProvider.generate(prompt);
            for (String chunk : response.split("(?<=[.!?])\\s+|\\n+")) {
                if (!chunk.isBlank()) {
                    send(emitter, "chunk", chunk);
                }
            }
            conversationService.addAssistantMessage(userId, request.conversationId(), response);
            send(emitter, "complete", "Response saved");
            chatMetrics.completed();
            emitter.complete();
        } catch (Exception exception) {
            chatMetrics.failed();
            try {
                send(emitter, "error", "Unable to generate a response");
            } catch (IOException ignored) {
                // The client may have disconnected while the provider failed.
            }
            emitter.completeWithError(exception);
        }
    }

    private void send(SseEmitter emitter, String event, String data) throws IOException {
        emitter.send(SseEmitter.event().name(event).data(data));
    }
}
