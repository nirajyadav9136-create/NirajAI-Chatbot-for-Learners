package com.Niraj17.NirajAI;

import com.Niraj17.NirajAI.rag.LocalEmbeddingService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalEmbeddingServiceTest {

    @Test
    void createsDeterministicConfiguredDimensionVector() {
        LocalEmbeddingService service = new LocalEmbeddingService(384);

        float[] first = service.embed("Spring Boot dependency injection");
        float[] second = service.embed("Spring Boot dependency injection");

        assertThat(first).hasSize(384);
        assertThat(first).containsExactly(second);
        for (float value : first) {
            assertThat(Float.isFinite(value)).isTrue();
        }
    }
}
