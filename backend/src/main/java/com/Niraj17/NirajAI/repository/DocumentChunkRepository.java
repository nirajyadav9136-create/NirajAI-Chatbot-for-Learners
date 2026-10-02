package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {
    long countByDocumentId(UUID documentId);
    List<DocumentChunk> findTop8ByContentContainingIgnoreCaseOrderByChunkIndexAsc(String query);
}
