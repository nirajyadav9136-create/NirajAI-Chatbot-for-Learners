CREATE TABLE user_activities (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    topic_id UUID REFERENCES topics (id) ON DELETE SET NULL,
    activity_type VARCHAR(20) NOT NULL,
    time_spent_minutes INTEGER NOT NULL DEFAULT 0,
    questions_attempted INTEGER NOT NULL DEFAULT 0,
    questions_correct INTEGER NOT NULL DEFAULT 0,
    score INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT user_activities_type_check CHECK (activity_type IN ('CHAT', 'QUIZ', 'LESSON', 'PRACTICE', 'DEBUG', 'INTERVIEW', 'PROJECT')),
    CONSTRAINT user_activities_time_check CHECK (time_spent_minutes >= 0),
    CONSTRAINT user_activities_attempted_check CHECK (questions_attempted >= 0),
    CONSTRAINT user_activities_correct_check CHECK (questions_correct >= 0 AND questions_correct <= questions_attempted),
    CONSTRAINT user_activities_score_check CHECK (score IS NULL OR score BETWEEN 0 AND 100)
);

CREATE TABLE ml_predictions (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    topic_id UUID REFERENCES topics (id) ON DELETE SET NULL,
    prediction_type VARCHAR(50) NOT NULL,
    result VARCHAR(4000) NOT NULL,
    model_version VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_user_activities_user_created ON user_activities (user_id, created_at);
CREATE INDEX idx_user_activities_topic_created ON user_activities (topic_id, created_at);
CREATE INDEX idx_ml_predictions_user_created ON ml_predictions (user_id, created_at);
