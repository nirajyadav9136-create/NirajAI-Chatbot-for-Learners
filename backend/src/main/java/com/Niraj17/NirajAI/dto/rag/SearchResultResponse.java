package com.Niraj17.NirajAI.dto.rag;

import java.util.UUID;

public record SearchResultResponse(UUID documentId, String title, String source, int chunkIndex, String content) {
}
