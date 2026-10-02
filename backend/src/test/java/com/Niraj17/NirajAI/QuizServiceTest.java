package com.Niraj17.NirajAI;

import com.Niraj17.NirajAI.dto.quiz.CreateQuizRequest;
import com.Niraj17.NirajAI.dto.quiz.QuizQuestionRequest;
import com.Niraj17.NirajAI.dto.quiz.QuizResponse;
import com.Niraj17.NirajAI.dto.quiz.QuizResultResponse;
import com.Niraj17.NirajAI.dto.quiz.SubmitQuizRequest;
import com.Niraj17.NirajAI.entity.QuestionType;
import com.Niraj17.NirajAI.entity.Quiz;
import com.Niraj17.NirajAI.entity.QuizAttempt;
import com.Niraj17.NirajAI.entity.QuizQuestion;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.entity.TopicCategory;
import com.Niraj17.NirajAI.entity.TopicDifficulty;
import com.Niraj17.NirajAI.entity.User;
import com.Niraj17.NirajAI.repository.QuizAnswerRepository;
import com.Niraj17.NirajAI.repository.QuizAttemptRepository;
import com.Niraj17.NirajAI.repository.QuizQuestionRepository;
import com.Niraj17.NirajAI.repository.QuizRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import com.Niraj17.NirajAI.repository.UserRepository;
import com.Niraj17.NirajAI.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock private QuizRepository quizRepository;
    @Mock private QuizQuestionRepository questionRepository;
    @Mock private QuizAttemptRepository attemptRepository;
    @Mock private QuizAnswerRepository answerRepository;
    @Mock private TopicRepository topicRepository;
    @Mock private UserRepository userRepository;

    private QuizService quizService;
    private QuizQuestion firstQuestion;
    private QuizQuestion secondQuestion;
    private Quiz quiz;
    private User user;

    @BeforeEach
    void setUp() {
        quizService = new QuizService(quizRepository, questionRepository, attemptRepository, answerRepository, topicRepository, userRepository);
        Topic topic = new Topic("Java", "java", "Java fundamentals", TopicCategory.BACKEND, TopicDifficulty.BEGINNER);
        user = new User("learner@example.com", "learner", "hash");
        quiz = new Quiz(topic, "Java basics", "BEGINNER");
        firstQuestion = new QuizQuestion(quiz, QuestionType.MCQ, "Which keyword defines a class?", "class,struct", "class", "Use class to declare a Java class.");
        secondQuestion = new QuizQuestion(quiz, QuestionType.TRUE_FALSE, "Java is statically typed.", "true,false", "true", "Java checks types at compile time.");
    }

    @Test
    void submissionScoresExactSupportedAnswersAndDoesNotExposeAnswerKey() {
        when(quizRepository.findById(quiz.getId())).thenReturn(Optional.of(quiz));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(questionRepository.findAllByQuizIdOrderByIdAsc(quiz.getId())).thenReturn(List.of(firstQuestion, secondQuestion));
        when(attemptRepository.save(any(QuizAttempt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        QuizResultResponse result = quizService.submit(user.getId(), quiz.getId(),
                new SubmitQuizRequest(Map.of(firstQuestion.getId(), "class", secondQuestion.getId(), "false")));
        QuizResponse safeQuiz = quizService.get(quiz.getId());

        assertThat(result.score()).isEqualTo(50);
        assertThat(result.correctAnswers()).isEqualTo(1);
        assertThat(safeQuiz.questions()).allSatisfy(question -> {
            assertThat(question.toString()).doesNotContain("correct");
        });
    }
}
