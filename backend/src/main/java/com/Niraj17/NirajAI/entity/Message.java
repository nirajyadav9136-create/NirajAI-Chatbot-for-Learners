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
@Table(name = "messages")
public class Message {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MessageRole role;

    @Column(nullable = false, length = 20000)
    private String content;

    @Column(name = "token_count")
    private Integer tokenCount;

    @Column(length = 4000)
    private String metadata;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Message() {
    }

    public Message(Conversation conversation, MessageRole role, String content) {
        this.id = UUID.randomUUID();
        this.conversation = conversation;
        this.role = role;
        this.content = content;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
        createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public Conversation getConversation() { return conversation; }
    public MessageRole getRole() { return role; }
    public String getContent() { return content; }
    public Integer getTokenCount() { return tokenCount; }
    public String getMetadata() { return metadata; }
    public Instant getCreatedAt() { return createdAt; }
}
