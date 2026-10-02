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
@Table(name = "quizzes")
public class Quiz {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 20)
    private String difficulty;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Quiz() {
    }

    public Quiz(Topic topic, String title, String difficulty) {
        this.id = UUID.randomUUID();
        this.topic = topic;
        this.title = title;
        this.difficulty = difficulty;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
        createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public Topic getTopic() { return topic; }
    public String getTitle() { return title; }
    public String getDifficulty() { return difficulty; }
    public Instant getCreatedAt() { return createdAt; }
}
