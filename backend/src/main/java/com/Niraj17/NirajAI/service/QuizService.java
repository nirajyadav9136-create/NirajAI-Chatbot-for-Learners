package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.quiz.CreateQuizRequest;
import com.Niraj17.NirajAI.dto.quiz.QuizQuestionRequest;
import com.Niraj17.NirajAI.dto.quiz.QuizQuestionResponse;
import com.Niraj17.NirajAI.dto.quiz.QuizResponse;
import com.Niraj17.NirajAI.dto.quiz.QuizResultResponse;
import com.Niraj17.NirajAI.dto.quiz.SubmitQuizRequest;
import com.Niraj17.NirajAI.entity.QuestionType;
import com.Niraj17.NirajAI.entity.Quiz;
import com.Niraj17.NirajAI.entity.QuizAnswer;
import com.Niraj17.NirajAI.entity.QuizAttempt;
import com.Niraj17.NirajAI.entity.QuizQuestion;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.repository.QuizAnswerRepository;
import com.Niraj17.NirajAI.repository.QuizAttemptRepository;
import com.Niraj17.NirajAI.repository.QuizQuestionRepository;
import com.Niraj17.NirajAI.repository.QuizRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final QuizAnswerRepository answerRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, QuizQuestionRepository questionRepository,
                       QuizAttemptRepository attemptRepository, QuizAnswerRepository answerRepository,
                       TopicRepository topicRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public QuizResponse create(CreateQuizRequest request) {
        Topic topic = topicRepository.findById(request.topicId()).orElseThrow(() -> notFound("Topic not found"));
        Quiz quiz = quizRepository.save(new Quiz(topic, request.title().trim(), request.difficulty().trim().toUpperCase()));
        List<QuizQuestion> questions = request.questions().stream().map(question -> toEntity(quiz, question)).toList();
        return toResponse(quiz, questionRepository.saveAll(questions));
    }

    @Transactional(readOnly = true)
    public List<QuizResponse> list() {
        return quizRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(quiz -> toResponse(quiz, questionRepository.findAllByQuizIdOrderByIdAsc(quiz.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public QuizResponse get(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> notFound("Quiz not found"));
        return toResponse(quiz, questionRepository.findAllByQuizIdOrderByIdAsc(quizId));
    }

    @Transactional
    public QuizResultResponse submit(UUID userId, UUID quizId, SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> notFound("Quiz not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> notFound("User not found"));
        List<QuizQuestion> questions = questionRepository.findAllByQuizIdOrderByIdAsc(quizId);
        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz has no questions");
        }
        int correct = 0;
        List<AnswerDecision> decisions = questions.stream().map(question -> {
            String submitted = request.answers().getOrDefault(question.getId(), "");
            boolean isCorrect = evaluate(question, submitted);
            return new AnswerDecision(question, submitted, isCorrect);
        }).toList();
        correct = (int) decisions.stream().filter(AnswerDecision::correct).count();
        int score = Math.round(correct * 100.0f / questions.size());
        QuizAttempt attempt = attemptRepository.save(new QuizAttempt(quiz, user, score));
        answerRepository.saveAll(decisions.stream()
                .map(decision -> new QuizAnswer(attempt, decision.question(), decision.submitted(), decision.correct()))
                .toList());
        return new QuizResultResponse(attempt.getId(), quizId, score, questions.size(), correct, attempt.getCompletedAt());
    }

    private boolean evaluate(QuizQuestion question, String submitted) {
        if (submitted == null) return false;
        if (question.getQuestionType() != QuestionType.MCQ && question.getQuestionType() != QuestionType.TRUE_FALSE) return false;
        return question.getCorrectAnswer().trim().equalsIgnoreCase(submitted.trim());
    }

    private QuizQuestion toEntity(Quiz quiz, QuizQuestionRequest request) {
        return new QuizQuestion(quiz, request.questionType(), request.question().trim(), request.options(),
                request.correctAnswer().trim(), request.explanation().trim());
    }

    private QuizResponse toResponse(Quiz quiz, List<QuizQuestion> questions) {
        return new QuizResponse(quiz.getId(), quiz.getTopic().getId(), quiz.getTitle(), quiz.getDifficulty(),
                questions.stream().map(question -> new QuizQuestionResponse(question.getId(), question.getQuestionType(), question.getQuestion(), question.getOptions())).toList());
    }

    private ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    private record AnswerDecision(QuizQuestion question, String submitted, boolean correct) {
    }
}
