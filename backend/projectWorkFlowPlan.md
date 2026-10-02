You are a senior Java backend engineer, AI application architect,
database architect, and Spring Boot developer.

I am building a production-quality AI-powered software engineering
learning platform called "DevTutor AI".

The frontend is a React + TypeScript + Vite + Tailwind CSS application
with a ChatGPT-style conversational interface.

Your task is to build the COMPLETE BACKEND for this application using
Spring Boot, PostgreSQL and Spring AI.

The backend must be designed so that a separate Python FastAPI service
can later provide machine-learning functionality.

IMPORTANT:

Do not build a generic CRUD backend.

Design this as a real AI-powered personalized learning platform.

Do not put all logic into controllers.

Use clean layered architecture.

Do not expose LLM API keys to the frontend.

Do not implement machine-learning algorithms in Java.

Python will be responsible for ML services.

Jupyter Notebook will be used for ML experimentation, training,
evaluation and analysis.

==================================================
1. TECHNOLOGY STACK
==================================================

Use:

Java 21 or the project's currently configured compatible Java version.

Spring Boot 4.x.

Spring AI 2.x compatible with the selected Spring Boot version.

Maven.

Spring Web.

Spring Security.

JWT authentication.

Spring Data JPA.

Hibernate.

PostgreSQL.

PostgreSQL pgvector.

Bean Validation.

Spring Boot Actuator.

Spring AI.

Lombok only if already used or clearly beneficial.

JUnit 5.

Mockito.

Testcontainers where appropriate.

Use Flyway for database migrations.

Do NOT use Hibernate auto-create/update for production schema management.

Use:

spring.jpa.hibernate.ddl-auto=validate

and Flyway migrations.

==================================================
2. ARCHITECTURE
==================================================

Use this architecture:

React frontend
        |
        | REST / SSE
        |
Spring Boot backend
        |
        +---- PostgreSQL
        |
        +---- pgvector
        |
        +---- Redis (prepare integration; optional initially)
        |
        +---- Spring AI
        |
        +---- LLM provider
        |
        +---- Python FastAPI ML service

Spring Boot is the main application.

Python is an independent ML service.

Do not tightly couple Spring Boot to Python.

Communicate with Python through REST APIs.

==================================================
3. PACKAGE STRUCTURE
==================================================

Use a clean package structure:

com.devtutor.ai

config/
controller/
dto/
entity/
repository/
service/
security/
exception/
mapper/
util/
ai/
rag/
learning/
quiz/
analytics/
client/
ml/

Continue with:

ai/
    ChatService
    AIProviderService
    PromptService
    ConversationMemoryService
    ToolService

rag/
    DocumentService
    DocumentChunkService
    EmbeddingService
    VectorSearchService
    RAGService

learning/
    LearningProfileService
    TopicService
    ProgressService
    RecommendationService

quiz/
    QuizService
    QuizEvaluationService
    QuestionGenerationService

analytics/
    LearningAnalyticsService
    UserActivityService

ml/
    MLServiceClient
    MLRecommendationClient
    MLSkillPredictionClient
    MLDifficultyPredictionClient

client/
    PythonMLClient

Adapt the structure when necessary.

==================================================
4. AUTHENTICATION

Implement secure authentication.

Features:

Register
Login
Refresh token
Logout
Get current user
Update profile
Change password
Forgot password architecture
Email verification architecture

Use:

Spring Security
JWT
BCrypt/secure password hashing

Never store plaintext passwords.

Create:

User
Role
RefreshToken

Entities.

Support:

USER
ADMIN

Use DTOs.

Never expose password hashes in API responses.

==================================================
5. USER PROFILE

Create:

UserProfile

Fields should include:

id
userId
displayName
bio
profileImageUrl
preferredLanguage
experienceLevel
learningGoal
createdAt
updatedAt

Experience levels:

BEGINNER
INTERMEDIATE
ADVANCED

Learning goals:

JOB_PREPARATION
COLLEGE
PROJECT_BUILDING
INTERVIEW
GENERAL_LEARNING

==================================================
6. LEARNING PROFILE

Create:

LearningProfile

Track:

preferredTopics
experienceLevel
learningGoal
dailyLearningGoal
weeklyLearningGoal
currentStreak
totalLearningMinutes

Create:

TopicProgress

Fields:

userId
topicId
skillScore
questionsAttempted
questionsCorrect
lastStudiedAt
timeSpentMinutes

Initially skillScore can be calculated using deterministic rules.

Prepare the architecture so Python ML can later replace
the rule-based calculation.

==================================================
7. TECHNOLOGY TOPICS

Create entities:

Topic
SubTopic
LearningResource

Seed the database with:

Frontend:

HTML
CSS
JavaScript
React
Tailwind CSS

Backend:

Java
Python
Spring Boot
FastAPI
Flask

Databases:

SQL
PostgreSQL
MongoDB
Database Design
Normalization
Indexes
Transactions
Joins
NoSQL

Additional topics:

REST API
Git
GitHub
Docker
Testing
Authentication
System Design
DSA
Cloud Computing

Each topic should have:

id
name
slug
description
category
difficulty

Use categories:

FRONTEND
BACKEND
DATABASE
DEVOPS
COMPUTER_SCIENCE
SOFTWARE_ENGINEERING

==================================================
8. CONVERSATIONS

Implement persistent conversations.

Entities:

Conversation
Message

Conversation:

id
userId
title
model
topicId
createdAt
updatedAt

Message:

id
conversationId
role
content
createdAt
tokenCount
metadata

Roles:

USER
ASSISTANT
SYSTEM

Endpoints:

POST /api/conversations
GET /api/conversations
GET /api/conversations/{id}
PATCH /api/conversations/{id}
DELETE /api/conversations/{id}

Messages:

POST /api/conversations/{id}/messages

==================================================
9. AI CHAT

Use Spring AI ChatClient.

Create:

AIChatService

The flow must be:

User message
↓
Authenticate user
↓
Load conversation
↓
Load recent conversation history
↓
Load learning profile
↓
Determine topic/context
↓
Retrieve relevant knowledge
↓
Build teaching prompt
↓
Call LLM
↓
Stream response
↓
Persist response
↓
Update learning analytics

Use Spring AI's ChatClient abstraction.

Do not directly implement provider-specific HTTP calls unless required.

Keep provider integration replaceable.

==================================================
10. STREAMING

Implement Server-Sent Events.

Endpoint:

POST /api/chat/stream

or an appropriate SSE design compatible with Spring Boot.

The frontend should receive:

partial response chunks

until the complete answer is generated.

The service architecture must support:

start
stream
complete
error
cancel

Do not block the application unnecessarily.

==================================================
11. AI TEACHER BEHAVIOR

Create a dedicated prompt-building service.

The AI must behave as a software engineering tutor.

System behavior:

You are DevTutor AI.

Your objective is to teach the learner, not simply answer questions.

Adapt explanations according to the user's skill level.

When appropriate:

Explain the concept.
Give a simple analogy.
Give a technical explanation.
Show code.
Explain the important parts of the code.
Mention common mistakes.
Ask a short comprehension question.
Provide a practice exercise.
Provide hints before revealing the answer.
Recommend what to study next.

For beginner learners:
use simpler language and examples.

For intermediate learners:
include implementation details and trade-offs.

For advanced learners:
include architecture, performance, security and design considerations.

Avoid unnecessary verbosity.

==================================================
12. TEACHING MODES

Support:

LEARN
PRACTICE
QUIZ
DEBUG
INTERVIEW
PROJECT
REVIEW

Create:

LearningMode enum.

The AI prompt must change based on the mode.

==================================================
13. QUIZ SYSTEM

Create:

Quiz
QuizQuestion
QuizAttempt
QuizAnswer

Support:

MCQ
TRUE_FALSE
SHORT_ANSWER
CODE_OUTPUT
CODE_DEBUGGING
CODING

Fields should include:

difficulty
topic
question
options
correctAnswer
explanation

Do not expose correct answers before submission.

==================================================
14. QUIZ GENERATION

Use the LLM to generate questions.

Validate generated responses.

Prefer structured output / JSON schema where supported.

Validate:

question
options
correct answer
difficulty
topic

Do not blindly trust model output.

==================================================
15. QUIZ EVALUATION

Implement deterministic evaluation where possible.

MCQ:
exact comparison.

True/False:
exact comparison.

Short answer:
use an evaluation service.

Coding:
initially store submitted code and use a controlled evaluation
architecture.

Never execute arbitrary user-submitted code directly on the
production Spring Boot server.

Prepare a future sandbox architecture.

==================================================
16. LEARNING RECOMMENDATIONS

Initially use rule-based recommendations.

Example:

If Java skill < 50:
recommend Java fundamentals.

If Java fundamentals completed:
recommend OOP.

If OOP completed:
recommend Collections.

If JavaScript fundamentals completed:
recommend DOM.

Then:

Spring Boot
React
Databases
etc.

Create:

RecommendationService

and:

Recommendation entity.

The architecture must later support Python ML predictions.

==================================================
17. PYTHON ML SERVICE

Create a client interface:

MLServiceClient

Do NOT implement ML models in Spring Boot.

Spring Boot will call a Python FastAPI service.

Expected Python endpoints:

POST /predict/skill

POST /predict/difficulty

POST /recommend/topic

POST /evaluate/answer

GET /health

Create configuration:

ml.service.base-url

Use RestClient or WebClient.

Create DTOs for requests and responses.

If Python service is unavailable:

DO NOT crash the main application.

Fallback to rule-based recommendations.

==================================================
18. MACHINE LEARNING DATA

Store useful learning events.

Create:

LearningSession
UserActivity
MLPrediction

Track:

userId
topicId
activityType
timeSpent
questionsAttempted
questionsCorrect
difficulty
score
timestamp

Activity types:

CHAT
QUIZ
LESSON
PRACTICE
DEBUG
INTERVIEW
PROJECT

Do not collect unnecessary personal data.

==================================================
19. RAG SYSTEM

Implement Retrieval-Augmented Generation.

Knowledge sources:

HTML documentation
CSS documentation
Java documentation
JavaScript documentation
React documentation
Tailwind documentation
Spring Boot documentation
Python documentation
FastAPI documentation
Flask documentation
PostgreSQL documentation
MongoDB documentation
Database concepts

Store documents in PostgreSQL.

Use pgvector for embeddings.

Create:

Document
DocumentChunk

Document fields:

id
title
source
topicId
content
createdAt

DocumentChunk:

id
documentId
content
chunkIndex
embedding
metadata

Use Spring AI's vector store abstractions where appropriate.

==================================================
20. EMBEDDINGS

Create an embedding pipeline:

Document
↓
Text extraction
↓
Chunking
↓
Embedding model
↓
pgvector

User question:

Question
↓
Embedding
↓
Similarity search
↓
Relevant chunks
↓
LLM

Use cosine similarity.

Use appropriate pgvector indexing once the dataset becomes large.

==================================================
21. RAG SAFETY

Do not allow arbitrary retrieved documents to override
the system teaching instructions.

Clearly distinguish:

system instructions
retrieved content
user content

Treat retrieved content as reference material.

==================================================
22. TOOL CALLING

Prepare AI tools for:

getUserProgress()
getTopicProgress()
getQuizHistory()
getLearningRecommendations()
searchLearningResources()
getCurrentTopic()
calculateScore()

Use Spring AI tool calling where appropriate.

Tools should be read-only unless an explicit user action is required.

==================================================
23. AI RESPONSE METADATA

Store metadata such as:

model
topic
teachingMode
tokens
latency
retrievalUsed
toolsUsed

Do not store secrets.

==================================================
24. FEEDBACK

Create:

AIResponseFeedback

Allow:

LIKE
DISLIKE

Optional:

feedbackText

Track:

messageId
userId
feedbackType
createdAt

This data will later be useful for AI evaluation.

==================================================
25. API DESIGN

Use REST conventions.

Example:

/api/auth/register
/api/auth/login
/api/auth/refresh
/api/auth/logout

/api/users/me
/api/users/me/profile

/api/topics
/api/topics/{id}

/api/progress
/api/progress/{topicId}

/api/conversations
/api/conversations/{id}

/api/chat/stream

/api/quizzes
/api/quizzes/{id}
/api/quizzes/{id}/submit

/api/recommendations

/api/learning/sessions

/api/feedback

/api/documents

/api/search

Use appropriate HTTP status codes.

==================================================
26. ERROR HANDLING

Create:

GlobalExceptionHandler

Use standardized API responses:

{
"success": false,
"message": "...",
"errorCode": "...",
"timestamp": "...",
"path": "..."
}

Handle:

ValidationException
AuthenticationException
AuthorizationException
ResourceNotFoundException
AIServiceException
MLServiceException
RAGException
DatabaseException

Do not expose internal stack traces to clients.

==================================================
27. VALIDATION

Use Jakarta Bean Validation.

Validate:

email
password
username
conversation title
message length
quiz submissions
document metadata

Prevent oversized requests.

==================================================
28. SECURITY

Implement:

JWT
BCrypt
CORS
Role-based authorization
Input validation

Protect:

/api/admin/**

from normal users.

Never trust user IDs supplied by the frontend.

Always derive user identity from authenticated JWT.

Users must only access their own:

conversations
messages
learning progress
quiz attempts
documents
feedback

==================================================
29. RATE LIMITING

Prepare rate limiting for:

AI chat
quiz generation
document processing

Redis may be used.

If Redis is not initially configured:

create an abstraction so rate limiting can be added without
rewriting controllers.

==================================================
30. DATABASE

Use PostgreSQL.

Use Flyway.

Do not use ddl-auto=create.

Create migrations:

V1__initial_schema.sql
V2__users.sql
V3__learning_topics.sql
V4__conversations.sql
V5__quizzes.sql
V6__learning_analytics.sql
V7__documents.sql
V8__pgvector.sql

Adapt numbering to the actual project.

Use UUIDs where appropriate.

Add:

created_at
updated_at

where appropriate.

Add indexes for:

user_id
conversation_id
topic_id
created_at

==================================================
31. PGVECTOR

Enable:

CREATE EXTENSION IF NOT EXISTS vector;

Use pgvector for document embeddings.

Do not create embeddings for every chat message by default.

Only embed:

learning documents
knowledge chunks
selected long-term memory if later required

==================================================
32. CONFIGURATION

Use environment variables.

Example:

DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD

JWT_SECRET

AI_PROVIDER
AI_MODEL

AI_API_KEY

EMBEDDING_MODEL

ML_SERVICE_URL

REDIS_URL

Never commit secrets.

Create:

.env.example

==================================================
33. AI PROVIDER ABSTRACTION

Do not make the entire application dependent on one provider.

Create:

AIProvider interface

or an equivalent Spring AI abstraction.

Allow future support for:

Gemini
OpenAI
Local model

The frontend should eventually be able to select a model,
but the backend must validate allowed models.

==================================================
34. ADMIN

Create admin APIs for:

Users
Topics
Documents
AI usage
Feedback
Learning analytics
ML predictions

Do not create an admin frontend yet.

==================================================
35. OBSERVABILITY

Add:

Spring Boot Actuator

Health endpoints:

/actuator/health

Monitor:

AI latency
request count
errors
database health

Use structured logging.

Do not log:

passwords
JWT tokens
API keys
sensitive user content unnecessarily

==================================================
36. TESTING

Create unit tests for:

AuthService
LearningProfileService
RecommendationService
QuizService
AI prompt construction
RAG retrieval logic

Create integration tests for:

Authentication
Conversation APIs
Quiz submission
Database repositories

Use Testcontainers where appropriate.

==================================================
37. SEED DATA

Create development seed data for:

Topics
Subtopics
Example learning resources

Do not create fake production users.

==================================================
38. DO NOT IMPLEMENT YET

Do NOT implement:

image generation
voice generation
social networking
payment system
mobile application
microservices for every feature
Kubernetes
complex multi-agent architecture

Keep the initial architecture modular but not unnecessarily distributed.

==================================================
39. DEVELOPMENT PHASES

Build in these phases.

PHASE 1:

Project setup
Database
Flyway
Security
JWT
Users

PHASE 2:

Topics
Learning profiles
Progress
Conversations
Messages

PHASE 3:

Spring AI
LLM integration
Prompt service
Streaming

PHASE 4:

RAG
pgvector
Document ingestion
Embeddings

PHASE 5:

Quiz system
Practice mode
Interview mode
Debug mode

PHASE 6:

Python FastAPI integration
ML client
Fallback recommendation system

PHASE 7:

Learning analytics
ML data collection

PHASE 8:

Tool calling
Personalized recommendations

PHASE 9:

Admin
Observability
Testing
Docker

==================================================
40. CODING RULES

Use:

DTO → Controller → Service → Repository

Do not expose JPA entities directly from REST controllers.

Use mappers.

Use constructor injection.

Avoid field injection.

Use interfaces where they provide actual value.

Avoid unnecessary abstractions.

Keep controllers thin.

Keep business logic inside services.

Keep database queries inside repositories.

Use transactions appropriately.

Use pagination for conversation history and large datasets.

Do not use SELECT * unnecessarily.

Do not put API keys in source code.

==================================================
41. API DOCUMENTATION

Add OpenAPI/Swagger documentation.

Document:

authentication
chat
conversations
topics
progress
quizzes
recommendations
documents

==================================================
42. DOCKER

Prepare:

Dockerfile

docker-compose.yml

Services:

postgres
redis
springboot

Optionally:

python-ml-service

Do not require Docker to run the application locally if the
developer wants to run Spring Boot directly.

==================================================
43. PYTHON SERVICE CONTRACT

Document the contract between Spring Boot and Python.

Example:

POST /predict/skill

Request:

{
"userId": "...",
"topicId": "...",
"features": {
"quizScore": 75,
"questionsAttempted": 20,
"questionsCorrect": 15,
"timeSpentMinutes": 120
}
}

Response:

{
"topicId": "...",
"skillScore": 0.72,
"confidence": 0.88,
"modelVersion": "skill-model-v1"
}

Do not implement the Python ML model inside this Spring Boot
repository.

==================================================
44. FUTURE JUPYTER ML PIPELINE

Prepare documentation explaining:

PostgreSQL learning data
↓
Python
↓
Pandas
↓
Feature engineering
↓
Jupyter Notebook
↓
Model training
↓
Model evaluation
↓
Model artifact
↓
FastAPI
↓
Spring Boot
↓
Recommendation

Potential models:

Random Forest
Gradient Boosting
Logistic Regression

Do not select a model simply because it is popular.

Evaluate models using appropriate metrics.

==================================================
45. DOCUMENTATION

Create:

README.md

docs/
├── architecture.md
├── database.md
├── authentication.md
├── ai-architecture.md
├── rag.md
├── ml-architecture.md
├── api.md
└── development.md

The documentation should explain:

Why Spring Boot?
Why PostgreSQL?
Why pgvector?
Why Python?
Why FastAPI?
Why Jupyter?
Why RAG?
Why ML?
How the services communicate.

==================================================
46. FINAL VALIDATION

Before considering the backend complete:

Run:

mvn clean test

Then:

mvn spring-boot:run

Verify:

database connection
Flyway migrations
JWT authentication
conversation creation
message persistence
topic APIs
progress APIs
quiz APIs
AI API
SSE streaming
RAG architecture
Python service fallback
validation
error handling
Actuator health

Fix compilation errors.

Fix test failures.

Fix warnings where practical.

Do not leave broken placeholder implementations.

==================================================
47. FINAL OUTPUT

After implementation report:

Project structure
Dependencies added
Database schema
API endpoints
Environment variables
How to run PostgreSQL
How to run Spring Boot
How to run Python ML service
How the frontend should connect
Current completed features
Remaining TODOs
Future ML roadmap

IMPORTANT:

Do not generate the entire project as one giant file.

Create real files.

Use production-quality architecture.

Do not implement fake AI APIs.

Where an external LLM API is required, create a clean provider
integration and configuration.

Do not expose secrets.

Start with Phase 1 and Phase 2, verify compilation/tests,
then continue through the remaining phases.


---

# 19. One change I'd make to your technology list

You mentioned:

> Java, Python, Spring Boot, MongoDB, FastAPI, Flask

I **wouldn't use both FastAPI and Flask** in this project.

Pick **FastAPI**.

Your architecture can be:

```text
Java
 ↓
Spring Boot
 ↓
Main backend

Python
 ↓
FastAPI
 ↓
ML service

You can learn Flask separately, but putting both Flask and FastAPI into this project doesn't add much architectural value.

Likewise, I wouldn't use MongoDB just because you want to learn MongoDB.

Your main database should remain:

PostgreSQL
+
pgvector

because your application has lots of relational data:

User
 ↓
Learning Profile
 ↓
Topics
 ↓
Progress
 ↓
Quiz
 ↓
Attempts
 ↓
Recommendations

PostgreSQL handles that very naturally, while pgvector lets you handle your RAG embeddings in the same database.