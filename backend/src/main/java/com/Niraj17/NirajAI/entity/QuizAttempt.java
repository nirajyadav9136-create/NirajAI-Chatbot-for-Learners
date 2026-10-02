package com.Niraj17.NirajAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int score;

    @Column(name = "completed_at", nullable = false)
    private Instant completedAt;

    protected QuizAttempt() {
    }

    public QuizAttempt(Quiz quiz, User user, int score) {
        this.id = UUID.randomUUID();
        this.quiz = quiz;
        this.user = user;
        this.score = score;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
        completedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public Quiz getQuiz() { return quiz; }
    public User getUser() { return user; }
    public int getScore() { return score; }
    public Instant getCompletedAt() { return completedAt; }
}
