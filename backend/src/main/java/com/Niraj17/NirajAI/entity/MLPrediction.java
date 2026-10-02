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
@Table(name = "ml_predictions")
public class MLPrediction {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "prediction_type", nullable = false, length = 50)
    private String predictionType;

    @Column(nullable = false, length = 4000)
    private String result;

    @Column(name = "model_version", length = 100)
    private String modelVersion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected MLPrediction() {
    }

    public MLPrediction(User user, Topic topic, String predictionType, String result, String modelVersion) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
        this.predictionType = predictionType;
        this.result = result;
        this.modelVersion = modelVersion;
    }

    @PrePersist
    void onCreate() { if (id == null) id = UUID.randomUUID(); createdAt = Instant.now(); }
}
