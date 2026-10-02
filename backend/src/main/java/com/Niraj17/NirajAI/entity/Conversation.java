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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 100)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_mode", nullable = false, length = 20)
    private LearningMode learningMode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Conversation() {
    }

    public Conversation(User user, String title, LearningMode learningMode, Topic topic, String model) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.title = title;
        this.learningMode = learningMode;
        this.topic = topic;
        this.model = model;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() { updatedAt = Instant.now(); }

    public void rename(String title) { this.title = title; }
    public UUID getId() { return id; }
    public User getUser() { return user; }
    public Topic getTopic() { return topic; }
    public String getTitle() { return title; }
    public String getModel() { return model; }
    public LearningMode getLearningMode() { return learningMode; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
