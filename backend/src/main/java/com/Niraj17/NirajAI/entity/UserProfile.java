package com.Niraj17.NirajAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(length = 1000)
    private String bio;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(name = "preferred_language", length = 50)
    private String preferredLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false, length = 20)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_goal", nullable = false, length = 30)
    private LearningGoal learningGoal;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected UserProfile() {
    }

    public UserProfile(User user) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.experienceLevel = ExperienceLevel.BEGINNER;
        this.learningGoal = LearningGoal.GENERAL_LEARNING;
    }

    @PrePersist
    void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void update(String displayName, String bio, String profileImageUrl, String preferredLanguage,
                       ExperienceLevel experienceLevel, LearningGoal learningGoal) {
        this.displayName = displayName;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.preferredLanguage = preferredLanguage;
        this.experienceLevel = experienceLevel;
        this.learningGoal = learningGoal;
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public String getPreferredLanguage() { return preferredLanguage; }
    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public LearningGoal getLearningGoal() { return learningGoal; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
