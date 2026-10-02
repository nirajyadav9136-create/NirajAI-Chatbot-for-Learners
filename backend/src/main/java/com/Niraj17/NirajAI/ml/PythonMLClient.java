package com.Niraj17.NirajAI.ml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class PythonMLClient implements MLServiceClient {

    private final RestClient restClient;
    private final String baseUrl;

    public PythonMLClient(RestClient.Builder restClientBuilder, @Value("${ml.service.base-url:}") String baseUrl) {
        this.baseUrl = baseUrl == null ? "" : baseUrl.trim();
        this.restClient = restClientBuilder.baseUrl(this.baseUrl.isBlank() ? "http://localhost:8000" : this.baseUrl).build();
    }

    @Override
    public Optional<SkillPredictionResponse> predictSkill(SkillPredictionRequest request) {
        if (baseUrl.isBlank()) return Optional.empty();
        try {
            SkillPredictionResponse response = restClient.post()
                    .uri("/predict/skill")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(SkillPredictionResponse.class);
            return Optional.ofNullable(response);
        } catch (RuntimeException exception) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isAvailable() {
        if (baseUrl.isBlank()) return false;
        try {
            restClient.get().uri("/health").retrieve().toBodilessEntity();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
