package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, UUID> {
    List<QuizQuestion> findAllByQuizIdOrderByIdAsc(UUID quizId);
}
