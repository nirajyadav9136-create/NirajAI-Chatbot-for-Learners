package com.Niraj17.NirajAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;

    @Column(name = "submitted_answer", nullable = false, length = 4000)
    private String submittedAnswer;

    @Column(nullable = false)
    private boolean correct;

    protected QuizAnswer() {
    }

    public QuizAnswer(QuizAttempt attempt, QuizQuestion question, String submittedAnswer, boolean correct) {
        this.id = UUID.randomUUID();
        this.attempt = attempt;
        this.question = question;
        this.submittedAnswer = submittedAnswer;
        this.correct = correct;
    }

    @PrePersist
    void onCreate() { if (id == null) id = UUID.randomUUID(); }
}
