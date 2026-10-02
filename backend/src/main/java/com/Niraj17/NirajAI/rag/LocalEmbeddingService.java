package com.Niraj17.NirajAI.rag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Primary
public class LocalEmbeddingService implements EmbeddingService {

    private final int dimensions;

    public LocalEmbeddingService(@Value("${rag.embedding-dimensions:384}") int dimensions) {
        if (dimensions < 8) {
            throw new IllegalArgumentException("Embedding dimensions must be at least 8");
        }
        this.dimensions = dimensions;
    }

    @Override
    public float[] embed(String text) {
        float[] vector = new float[dimensions];
        byte[] digest = digest(text == null ? "" : text);
        for (int index = 0; index < dimensions; index++) {
            int value = digest[index % digest.length] & 0xff;
            vector[index] = (value / 127.5f) - 1.0f;
        }
        return vector;
    }

    private byte[] digest(String text) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }
}
