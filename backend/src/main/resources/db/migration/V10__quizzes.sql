CREATE TABLE quizzes (
    id UUID PRIMARY KEY,
    topic_id UUID NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE quiz_questions (
    id UUID PRIMARY KEY,
    quiz_id UUID NOT NULL REFERENCES quizzes (id) ON DELETE CASCADE,
    question_type VARCHAR(30) NOT NULL,
    question VARCHAR(4000) NOT NULL,
    options VARCHAR(4000),
    correct_answer VARCHAR(1000) NOT NULL,
    explanation VARCHAR(4000) NOT NULL
);

CREATE TABLE quiz_attempts (
    id UUID PRIMARY KEY,
    quiz_id UUID NOT NULL REFERENCES quizzes (id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    score INTEGER NOT NULL CHECK (score BETWEEN 0 AND 100),
    completed_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE quiz_answers (
    id UUID PRIMARY KEY,
    attempt_id UUID NOT NULL REFERENCES quiz_attempts (id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES quiz_questions (id) ON DELETE CASCADE,
    submitted_answer VARCHAR(4000) NOT NULL,
    correct BOOLEAN NOT NULL
);

CREATE INDEX idx_quiz_questions_quiz_id ON quiz_questions (quiz_id);
CREATE INDEX idx_quiz_attempts_user_id ON quiz_attempts (user_id);
