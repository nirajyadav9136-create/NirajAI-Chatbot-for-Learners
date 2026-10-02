package com.Niraj17.NirajAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 30)
    private QuestionType questionType;

    @Column(nullable = false, length = 4000)
    private String question;

    @Column(length = 4000)
    private String options;

    @Column(name = "correct_answer", nullable = false, length = 1000)
    private String correctAnswer;

    @Column(nullable = false, length = 4000)
    private String explanation;

    protected QuizQuestion() {
    }

    public QuizQuestion(Quiz quiz, QuestionType questionType, String question, String options, String correctAnswer, String explanation) {
        this.id = UUID.randomUUID();
        this.quiz = quiz;
        this.questionType = questionType;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    @PrePersist
    void onCreate() { if (id == null) id = UUID.randomUUID(); }

    public UUID getId() { return id; }
    public Quiz getQuiz() { return quiz; }
    public QuestionType getQuestionType() { return questionType; }
    public String getQuestion() { return question; }
    public String getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getExplanation() { return explanation; }
}
