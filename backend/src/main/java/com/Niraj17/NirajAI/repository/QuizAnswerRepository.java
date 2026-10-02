package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, UUID> {
}
