package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.progress.ProgressResponse;
import com.Niraj17.NirajAI.dto.progress.ProgressUpdateRequest;
import com.Niraj17.NirajAI.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping
    public List<ProgressResponse> listProgress(Authentication authentication) {
        return progressService.listProgress(userId(authentication));
    }

    @GetMapping("/{topicId}")
    public ProgressResponse getProgress(Authentication authentication, @PathVariable UUID topicId) {
        return progressService.getProgress(userId(authentication), topicId);
    }

    @PatchMapping("/{topicId}")
    public ProgressResponse updateProgress(
            Authentication authentication,
            @PathVariable UUID topicId,
            @Valid @RequestBody ProgressUpdateRequest request) {
        return progressService.recordProgress(userId(authentication), topicId, request);
    }

    private UUID userId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
