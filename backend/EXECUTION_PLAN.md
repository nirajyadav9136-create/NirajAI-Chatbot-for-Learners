# DevTutor AI Backend Execution Plan

This plan converts `projectWorkFlowPlan.md` into incremental, testable delivery chunks. Each chunk must leave the application buildable and must add focused tests before moving forward.

## Delivery order

1. **Foundation and runtime configuration**
   - Add the web, validation, persistence, security, actuator, PostgreSQL, Flyway, and test dependencies.
   - Establish environment-based configuration, test profile configuration, schema migration conventions, API error envelope, and package boundaries.
   - Exit criteria: application context starts, migration strategy is explicit, and the baseline test suite passes.

2. **Identity and user profiles**
   - Add `User`, `Role`, `RefreshToken`, `UserProfile`, enums, repositories, DTOs, validation, password hashing, JWT service/filter, auth service, and auth endpoints.
   - Add ownership rules and focused service/controller tests.
   - Exit criteria: register, login, refresh, logout, current-user, profile update, and password change work without exposing secrets.

3. **Learning domain**
   - Add topics, subtopics, resources, learning profiles, topic progress, learning sessions, and seed data.
   - Add deterministic progress calculation and recommendation-ready service interfaces.
   - Exit criteria: authenticated users can read topics and maintain their own progress.

4. **Conversations and chat contract**
   - Add conversations, messages, teaching modes, metadata, feedback, DTOs, ownership checks, pagination, and REST endpoints.
   - Add a provider-neutral chat service seam and SSE response contract with a deterministic local fallback for development.
   - Exit criteria: conversation CRUD and message flow are persisted and testable without an external LLM key.

5. **Spring AI and RAG**
   - Add Spring AI provider configuration behind an application abstraction.
   - Add documents, chunks, embeddings, vector search, retrieval safety, prompt construction, and streamed AI responses.
   - Exit criteria: external provider settings are optional at startup, retrieved text cannot override tutor instructions, and AI failures have stable API errors.

6. **Quizzes and recommendations**
   - Add quizzes, questions, attempts, answers, deterministic evaluation, generated-question validation, and rule-based recommendations.
   - Do not reveal answer keys before submission and never execute submitted code in-process.
   - Exit criteria: quiz creation/submission and recommendation endpoints have service and integration coverage.

7. **Python ML integration**
   - Add `MLServiceClient`, typed request/response DTOs, timeout/error handling, health probing, and rule-based fallback behavior.
   - Add prediction and activity persistence needed for later model training.
   - Exit criteria: ML unavailability does not take down core backend flows.

8. **Hardening and operational readiness**
   - Add rate-limit abstraction, CORS policy, admin authorization, structured logging, metrics, request limits, database indexes, and production-safe exception handling.
   - Add Testcontainers coverage for PostgreSQL/pgvector where available, API security tests, and a documented local runbook.
   - Exit criteria: full test/build validation passes and no secrets or schema auto-generation are required.

## Working rules

- Keep controllers thin; business decisions belong in services.
- Derive user identity from the authenticated principal, never from a request body user ID.
- Use Flyway for production schema changes and `ddl-auto=validate` outside tests.
- Keep provider-specific AI and Python integration behind interfaces.
- Add a focused test with each behavior change and run the narrowest useful validation immediately.
- Do not implement payments, voice/image generation, social features, mobile clients, Kubernetes, or feature-per-service microservices in this phase.

## Current checkpoint

**Execution checkpoint complete:** the backend now has a real sibling Python FastAPI ML service at `../ml-service`, typed ML endpoints, Spring-to-Python connection through `PythonMLClient`, rule-based fallback, learning activity and prediction persistence, AI metrics, standardized API errors, security JSON responses, explicit CORS, and final test validation. Production deployment still requires PostgreSQL with pgvector, configured secrets, and replacement of baseline local/provider stubs with selected model providers.
