CREATE TABLE recommendations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    topic_id UUID NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
    reason VARCHAR(1000) NOT NULL,
    priority INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_recommendations_user_priority ON recommendations (user_id, priority, created_at);
