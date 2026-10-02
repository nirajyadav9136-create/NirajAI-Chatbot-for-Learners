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
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Column(nullable = false)
    private int priority;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Recommendation() {
    }

    public Recommendation(User user, Topic topic, String reason, int priority) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
        this.reason = reason;
        this.priority = priority;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
        createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public Topic getTopic() { return topic; }
    public String getReason() { return reason; }
    public int getPriority() { return priority; }
    public Instant getCreatedAt() { return createdAt; }
}
