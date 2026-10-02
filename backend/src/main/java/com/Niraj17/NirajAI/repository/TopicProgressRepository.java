package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.TopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicProgressRepository extends JpaRepository<TopicProgress, UUID> {

    List<TopicProgress> findAllByUserIdOrderByLastStudiedAtDesc(UUID userId);

    Optional<TopicProgress> findByUserIdAndTopicId(UUID userId, UUID topicId);
}
