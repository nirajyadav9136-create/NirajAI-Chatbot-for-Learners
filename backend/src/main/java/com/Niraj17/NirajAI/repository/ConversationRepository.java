package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    List<Conversation> findAllByUserIdOrderByUpdatedAtDesc(UUID userId);

    @EntityGraph(attributePaths = "topic")
    Optional<Conversation> findByIdAndUserId(UUID id, UUID userId);
}
