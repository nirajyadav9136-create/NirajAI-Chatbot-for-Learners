package com.Niraj17.NirajAI.dto.rag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateDocumentRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 500) String source,
        UUID topicId,
        @NotBlank @Size(max = 50000) String content
) {
}
