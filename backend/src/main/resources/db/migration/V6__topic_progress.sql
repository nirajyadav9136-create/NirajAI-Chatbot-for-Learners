CREATE TABLE topic_progress (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    topic_id UUID NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
    skill_score INTEGER NOT NULL DEFAULT 0,
    questions_attempted INTEGER NOT NULL DEFAULT 0,
    questions_correct INTEGER NOT NULL DEFAULT 0,
    time_spent_minutes INTEGER NOT NULL DEFAULT 0,
    last_studied_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT uk_topic_progress_user_topic UNIQUE (user_id, topic_id),
    CONSTRAINT topic_progress_score_check CHECK (skill_score BETWEEN 0 AND 100),
    CONSTRAINT topic_progress_attempted_check CHECK (questions_attempted >= 0),
    CONSTRAINT topic_progress_correct_check CHECK (questions_correct >= 0 AND questions_correct <= questions_attempted),
    CONSTRAINT topic_progress_time_check CHECK (time_spent_minutes >= 0)
);

CREATE INDEX idx_topic_progress_user_id ON topic_progress (user_id);
CREATE INDEX idx_topic_progress_topic_id ON topic_progress (topic_id);
