package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findAllByOrderByCreatedAtDesc();
}
