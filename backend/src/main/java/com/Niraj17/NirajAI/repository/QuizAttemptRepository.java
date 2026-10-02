package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, UUID> {
    List<QuizAttempt> findAllByUserIdOrderByCompletedAtDesc(UUID userId);
}
