CREATE TABLE conversations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    topic_id UUID REFERENCES topics (id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    model VARCHAR(100),
    learning_mode VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT conversations_mode_check CHECK (learning_mode IN ('LEARN', 'PRACTICE', 'QUIZ', 'DEBUG', 'INTERVIEW', 'PROJECT', 'REVIEW'))
);

CREATE TABLE messages (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL REFERENCES conversations (id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL,
    content VARCHAR(20000) NOT NULL,
    token_count INTEGER,
    metadata VARCHAR(4000),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT messages_role_check CHECK (role IN ('USER', 'ASSISTANT', 'SYSTEM'))
);

CREATE INDEX idx_conversations_user_updated ON conversations (user_id, updated_at);
CREATE INDEX idx_messages_conversation_created ON messages (conversation_id, created_at);
