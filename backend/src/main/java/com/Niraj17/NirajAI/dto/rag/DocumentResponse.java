package com.Niraj17.NirajAI.dto.rag;

import java.time.Instant;
import java.util.UUID;

public record DocumentResponse(UUID id, String title, String source, UUID topicId, int chunkCount, Instant createdAt) {
}
