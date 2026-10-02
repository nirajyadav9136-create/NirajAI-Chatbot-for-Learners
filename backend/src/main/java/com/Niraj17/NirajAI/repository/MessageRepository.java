package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByConversationIdOrderByCreatedAtAsc(UUID conversationId);
}
