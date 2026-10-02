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

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_activities")
public class UserActivity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 20)
    private ActivityType activityType;

    @Column(name = "time_spent_minutes", nullable = false)
    private int timeSpentMinutes;

    @Column(name = "questions_attempted", nullable = false)
    private int questionsAttempted;

    @Column(name = "questions_correct", nullable = false)
    private int questionsCorrect;

    @Column
    private Integer score;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected UserActivity() {
    }

    public UserActivity(User user, Topic topic, ActivityType activityType, int timeSpentMinutes,
                        int questionsAttempted, int questionsCorrect, Integer score) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
        this.activityType = activityType;
        this.timeSpentMinutes = timeSpentMinutes;
        this.questionsAttempted = questionsAttempted;
        this.questionsCorrect = questionsCorrect;
        this.score = score;
    }

    @PrePersist
    void onCreate() { if (id == null) id = UUID.randomUUID(); createdAt = Instant.now(); }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public Topic getTopic() { return topic; }
    public ActivityType getActivityType() { return activityType; }
    public Instant getCreatedAt() { return createdAt; }
}
