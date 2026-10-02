package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.conversation.ConversationResponse;
import com.Niraj17.NirajAI.dto.conversation.CreateConversationRequest;
import com.Niraj17.NirajAI.dto.conversation.CreateMessageRequest;
import com.Niraj17.NirajAI.dto.conversation.MessageResponse;
import com.Niraj17.NirajAI.dto.conversation.RenameConversationRequest;
import com.Niraj17.NirajAI.entity.Conversation;
import com.Niraj17.NirajAI.entity.LearningMode;
import com.Niraj17.NirajAI.entity.Message;
import com.Niraj17.NirajAI.entity.MessageRole;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.repository.ConversationRepository;
import com.Niraj17.NirajAI.repository.MessageRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository,
                               UserRepository userRepository, TopicRepository topicRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public ConversationResponse create(UUID userId, CreateConversationRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> notFound("User not found"));
        Topic topic = request.topicId() == null ? null : topicRepository.findById(request.topicId())
                .orElseThrow(() -> notFound("Topic not found"));
        Conversation conversation = new Conversation(user, request.title().trim(),
                request.learningMode() == null ? LearningMode.LEARN : request.learningMode(), topic, request.model());
        return toResponse(conversationRepository.save(conversation), List.of());
    }

    @Transactional(readOnly = true)
    public List<ConversationResponse> list(UUID userId) {
        return conversationRepository.findAllByUserIdOrderByUpdatedAtDesc(userId).stream()
                .map(conversation -> toResponse(conversation, List.of()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ConversationResponse get(UUID userId, UUID conversationId) {
        Conversation conversation = findOwned(userId, conversationId);
        return toResponse(conversation, messageRepository.findAllByConversationIdOrderByCreatedAtAsc(conversationId));
    }

    @Transactional
    public ConversationResponse rename(UUID userId, UUID conversationId, RenameConversationRequest request) {
        Conversation conversation = findOwned(userId, conversationId);
        conversation.rename(request.title().trim());
        return toResponse(conversationRepository.save(conversation), messageRepository.findAllByConversationIdOrderByCreatedAtAsc(conversationId));
    }

    @Transactional
    public MessageResponse addUserMessage(UUID userId, UUID conversationId, CreateMessageRequest request) {
        Conversation conversation = findOwned(userId, conversationId);
        return toMessageResponse(messageRepository.save(new Message(conversation, MessageRole.USER, request.content().trim())));
    }

    @Transactional
    public MessageResponse addAssistantMessage(UUID userId, UUID conversationId, String content) {
        Conversation conversation = findOwned(userId, conversationId);
        return toMessageResponse(messageRepository.save(new Message(conversation, MessageRole.ASSISTANT, content)));
    }

    @Transactional(readOnly = true)
    public Conversation findOwnedConversation(UUID userId, UUID conversationId) {
        return findOwned(userId, conversationId);
    }

    @Transactional
    public void delete(UUID userId, UUID conversationId) {
        conversationRepository.delete(findOwned(userId, conversationId));
    }

    private Conversation findOwned(UUID userId, UUID conversationId) {
        return conversationRepository.findByIdAndUserId(conversationId, userId)
                .orElseThrow(() -> notFound("Conversation not found"));
    }

    private ConversationResponse toResponse(Conversation conversation, List<Message> messages) {
        return new ConversationResponse(
                conversation.getId(), conversation.getTitle(), conversation.getModel(), conversation.getLearningMode(),
                conversation.getTopic() == null ? null : conversation.getTopic().getId(), conversation.getCreatedAt(), conversation.getUpdatedAt(),
                messages.stream().map(this::toMessageResponse).toList());
    }

    private MessageResponse toMessageResponse(Message message) {
        return new MessageResponse(message.getId(), message.getRole(), message.getContent(), message.getCreatedAt());
    }

    private ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
