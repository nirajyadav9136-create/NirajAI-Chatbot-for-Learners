CREATE TABLE user_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    display_name VARCHAR(100),
    bio VARCHAR(1000),
    profile_image_url VARCHAR(500),
    preferred_language VARCHAR(50),
    experience_level VARCHAR(20) NOT NULL,
    learning_goal VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT user_profiles_experience_check CHECK (experience_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
    CONSTRAINT user_profiles_goal_check CHECK (learning_goal IN ('JOB_PREPARATION', 'COLLEGE', 'PROJECT_BUILDING', 'INTERVIEW', 'GENERAL_LEARNING'))
);
