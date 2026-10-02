CREATE TABLE topics (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(120) NOT NULL UNIQUE,
    description VARCHAR(2000) NOT NULL,
    category VARCHAR(30) NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT topics_category_check CHECK (category IN ('FRONTEND', 'BACKEND', 'DATABASE', 'DEVOPS', 'COMPUTER_SCIENCE', 'SOFTWARE_ENGINEERING')),
    CONSTRAINT topics_difficulty_check CHECK (difficulty IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED'))
);

CREATE INDEX idx_topics_category ON topics (category);

INSERT INTO topics (id, name, slug, description, category, difficulty, created_at, updated_at) VALUES
('10000000-0000-0000-0000-000000000001', 'HTML', 'html', 'Structure accessible web pages with semantic markup.', 'FRONTEND', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('10000000-0000-0000-0000-000000000002', 'CSS', 'css', 'Style responsive interfaces with modern CSS layout and design techniques.', 'FRONTEND', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('10000000-0000-0000-0000-000000000003', 'JavaScript', 'javascript', 'Learn the language and browser APIs that power interactive web applications.', 'FRONTEND', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('10000000-0000-0000-0000-000000000004', 'React', 'react', 'Build component-based user interfaces with React.', 'FRONTEND', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('10000000-0000-0000-0000-000000000005', 'Tailwind CSS', 'tailwind-css', 'Compose responsive interfaces using utility-first CSS.', 'FRONTEND', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('20000000-0000-0000-0000-000000000001', 'Java', 'java', 'Learn object-oriented programming and the Java platform.', 'BACKEND', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('20000000-0000-0000-0000-000000000002', 'Python', 'python', 'Build readable programs and services with Python.', 'BACKEND', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('20000000-0000-0000-0000-000000000003', 'Spring Boot', 'spring-boot', 'Create production-ready Java web services with Spring Boot.', 'BACKEND', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('20000000-0000-0000-0000-000000000004', 'FastAPI', 'fastapi', 'Build typed, high-performance Python APIs.', 'BACKEND', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('20000000-0000-0000-0000-000000000005', 'Flask', 'flask', 'Create lightweight Python web applications with Flask.', 'BACKEND', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000001', 'SQL', 'sql', 'Query and transform relational data with SQL.', 'DATABASE', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000002', 'PostgreSQL', 'postgresql', 'Use PostgreSQL features for reliable application data.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000003', 'MongoDB', 'mongodb', 'Model and query document-oriented data.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000004', 'Database Design', 'database-design', 'Design maintainable schemas and data relationships.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000005', 'Normalization', 'normalization', 'Reduce redundancy while preserving useful relational structure.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000006', 'Indexes', 'indexes', 'Improve query performance with appropriate indexes.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000007', 'Transactions', 'transactions', 'Keep multi-step data changes consistent and isolated.', 'DATABASE', 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000008', 'Joins', 'joins', 'Combine related records across relational tables.', 'DATABASE', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('30000000-0000-0000-0000-000000000009', 'NoSQL', 'nosql', 'Choose and use non-relational data models.', 'DATABASE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000001', 'REST API', 'rest-api', 'Design predictable HTTP APIs for client applications.', 'SOFTWARE_ENGINEERING', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000002', 'Git', 'git', 'Track changes and collaborate safely with version control.', 'SOFTWARE_ENGINEERING', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000003', 'GitHub', 'github', 'Collaborate on code using GitHub workflows.', 'SOFTWARE_ENGINEERING', 'BEGINNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000004', 'Docker', 'docker', 'Package and run applications consistently with containers.', 'DEVOPS', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000005', 'Testing', 'testing', 'Build confidence with unit, integration, and contract tests.', 'SOFTWARE_ENGINEERING', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000006', 'Authentication', 'authentication', 'Protect applications with identity, sessions, and authorization.', 'SOFTWARE_ENGINEERING', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000007', 'System Design', 'system-design', 'Reason about scalable, reliable software architectures.', 'SOFTWARE_ENGINEERING', 'ADVANCED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000008', 'DSA', 'dsa', 'Practice data structures and algorithms for problem solving.', 'COMPUTER_SCIENCE', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('40000000-0000-0000-0000-000000000009', 'Cloud Computing', 'cloud-computing', 'Understand the primitives and trade-offs of cloud platforms.', 'DEVOPS', 'INTERMEDIATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
