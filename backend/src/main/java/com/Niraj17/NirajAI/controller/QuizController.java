package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.quiz.CreateQuizRequest;
import com.Niraj17.NirajAI.dto.quiz.QuizResponse;
import com.Niraj17.NirajAI.dto.quiz.QuizResultResponse;
import com.Niraj17.NirajAI.dto.quiz.SubmitQuizRequest;
import com.Niraj17.NirajAI.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResponse create(@Valid @RequestBody CreateQuizRequest request) {
        return quizService.create(request);
    }

    @GetMapping
    public List<QuizResponse> list() {
        return quizService.list();
    }

    @GetMapping("/{quizId}")
    public QuizResponse get(@PathVariable UUID quizId) {
        return quizService.get(quizId);
    }

    @PostMapping("/{quizId}/submit")
    public QuizResultResponse submit(Authentication authentication, @PathVariable UUID quizId,
                                     @Valid @RequestBody SubmitQuizRequest request) {
        return quizService.submit(UUID.fromString(authentication.getName()), quizId, request);
    }
}
