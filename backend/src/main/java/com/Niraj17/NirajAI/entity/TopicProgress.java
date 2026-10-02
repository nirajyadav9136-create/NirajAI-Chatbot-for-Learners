package com.Niraj17.NirajAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "topic_progress", uniqueConstraints = @UniqueConstraint(name = "uk_topic_progress_user_topic", columnNames = {"user_id", "topic_id"}))
public class TopicProgress {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "skill_score", nullable = false)
    private int skillScore;

    @Column(name = "questions_attempted", nullable = false)
    private int questionsAttempted;

    @Column(name = "questions_correct", nullable = false)
    private int questionsCorrect;

    @Column(name = "time_spent_minutes", nullable = false)
    private int timeSpentMinutes;

    @Column(name = "last_studied_at")
    private Instant lastStudiedAt;

    protected TopicProgress() {
    }

    public TopicProgress(User user, Topic topic) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
    }

    @PrePersist
    void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public void recordActivity(int attempted, int correct, int minutes) {
        if (attempted < 0 || correct < 0 || minutes < 0 || correct > attempted) {
            throw new IllegalArgumentException("Progress values are invalid");
        }
        questionsAttempted += attempted;
        questionsCorrect += correct;
        timeSpentMinutes += minutes;
        skillScore = questionsAttempted == 0 ? 0 : Math.round((questionsCorrect * 100.0f) / questionsAttempted);
        lastStudiedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public Topic getTopic() { return topic; }
    public int getSkillScore() { return skillScore; }
    public int getQuestionsAttempted() { return questionsAttempted; }
    public int getQuestionsCorrect() { return questionsCorrect; }
    public int getTimeSpentMinutes() { return timeSpentMinutes; }
    public Instant getLastStudiedAt() { return lastStudiedAt; }
}
