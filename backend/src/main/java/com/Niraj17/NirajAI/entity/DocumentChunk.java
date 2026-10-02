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
@Table(name = "document_chunks")
public class DocumentChunk {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(nullable = false, length = 4000)
    private String content;

    @Column(name = "chunk_index", nullable = false)
    private int chunkIndex;

    @Column(length = 2000)
    private String metadata;

    protected DocumentChunk() {
    }

    public DocumentChunk(Document document, String content, int chunkIndex) {
        this.id = UUID.randomUUID();
        this.document = document;
        this.content = content;
        this.chunkIndex = chunkIndex;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID();
    }

    public UUID getId() { return id; }
    public Document getDocument() { return document; }
    public String getContent() { return content; }
    public int getChunkIndex() { return chunkIndex; }
    public String getMetadata() { return metadata; }
}
