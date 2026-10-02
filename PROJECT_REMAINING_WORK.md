# NirajAI Project Remaining Work

This document compares the current Spring Boot backend and React frontend, identifies what is already implemented, and lists the remaining work required to reach a production-ready NirajAI learning platform.

## 1. Current State Summary

### Backend

The backend is a functional Spring Boot foundation with these implemented areas:

- Spring Boot 4.1.1, Java 21 target, Maven
- Spring Web, Validation, JPA, Security, Actuator
- PostgreSQL and Flyway configuration
- `ddl-auto=validate` for non-test environments
- H2 test profile
- JWT access tokens
- BCrypt password hashing
- Refresh-token persistence and logout
- Registration, login, refresh, logout, and current-user APIs
- User profiles and password change
- Topic catalog with seeded development topics
- Per-user topic progress and deterministic skill scores
- Conversation and message persistence
- Conversation ownership checks
- Teaching modes
- Local provider-neutral tutor response service
- Authenticated SSE chat endpoint
- Document and document-chunk persistence
- Keyword retrieval fallback for RAG
- Prompt reference-context safety boundary
- Embedding abstraction and pgvector migration preparation
- Quiz/question/attempt/answer persistence
- MCQ and true/false evaluation
- Rule-based recommendations
- Independent Python FastAPI ML service
- Spring-to-Python ML client with failure-tolerant fallback
- User activity and ML prediction persistence
- Chat metrics through Actuator/Micrometer
- Standardized controller error responses
- JSON security responses
- Environment-controlled CORS

### Frontend

The frontend is a working React/Vite mock application with:

- React 19 and Vite
- React Router routes for login, register, chat, settings, and profile
- Responsive desktop/mobile shell
- Sidebar and mobile drawer
- Mock conversation history
- New-chat creation in local state
- LocalStorage conversation persistence
- Mock assistant responses
- Markdown rendering
- Syntax-highlighted code blocks
- Copy message controls
- Basic edit-message flow
- Mock login/register state
- Basic settings and profile pages
- Attachment and voice button visual placeholders
- Model selector visual control

The frontend is currently a mock-first UI. It is not connected to the Spring Boot backend yet.

## 2. Verified Validation

### Backend

The latest Spring Boot test run passes:

- `AuthControllerTest`: 3 tests
- `LocalEmbeddingServiceTest`: 1 test
- `NirajAiApplicationTests`: 1 test
- `QuizServiceTest`: 1 test
- `TutorPromptServiceTest`: 2 tests

Total: 8 tests passed.

### ML service

The FastAPI test suite passes:

- Health endpoint
- Skill prediction endpoint
- Exact MCQ answer evaluation

Total: 3 tests passed.

### Frontend

`npm run build` succeeds.

The build reports a bundle-size warning because the main JavaScript bundle is above 500 kB after minification. This is not a build failure, but code splitting should be added before production release.

`npm run lint` completes with these warnings:

- `MessageComposer.jsx`: unused `onSuggestionPick` parameter
- `Sidebar.jsx`: unused `User` import
- `Sidebar.jsx`: unused `NavLink` import

## 3. Backend Work Remaining

The backend is functional, but several items from the original workflow plan are still partial or production-oriented.

### 3.1 Real Spring AI provider integration

Current state:

- The backend has an `AIProvider` abstraction.
- A deterministic local tutor provider is active.
- Provider configuration properties exist.

Remaining:

- Add the selected Spring AI starter and provider adapter.
- Implement a real `ChatClient`-based provider.
- Stream provider output directly into SSE chunks.
- Validate allowed model identifiers server-side.
- Add provider timeout, retry, and circuit-breaker behavior.
- Record model, latency, token usage, and provider errors.
- Add integration tests with a mocked provider.

### 3.2 Real embeddings and vector retrieval

Current state:

- `EmbeddingService` abstraction exists.
- A deterministic local embedding fallback exists.
- pgvector migration and HNSW index preparation exist.
- Keyword search works.

Remaining:

- Connect a real Spring AI embedding model.
- Persist generated vectors for document chunks.
- Implement cosine-similarity retrieval through pgvector.
- Make vector search fallback to keyword search when disabled or unavailable.
- Verify the migration against an actual PostgreSQL pgvector instance.
- Add retrieval ranking and top-k configuration.
- Add document reprocessing and embedding versioning.

### 3.3 Learning domain completeness

The original plan mentions additional domain objects that are not yet fully implemented:

- `SubTopic`
- `LearningResource`
- `LearningProfile`
- `LearningSession`
- More complete analytics workflows
- Activity recording from every chat, quiz, lesson, and practice action

### 3.4 Authentication completeness

Core JWT authentication is implemented. Remaining production workflows include:

- Refresh-token rotation and reuse detection
- Forgot-password request and reset-token flow
- Email verification flow
- Email delivery integration
- Account disable/enable administration
- Login attempt and security event auditing
- Stronger production JWT secret enforcement
- Token revocation strategy for access tokens if required

### 3.5 Quiz completeness

Implemented:

- Quiz creation and retrieval
- Safe question responses
- MCQ and true/false scoring

Remaining:

- Short-answer evaluation through the Python service
- Validated LLM question generation
- Coding-question sandbox architecture
- Quiz history API
- Per-question feedback response
- Difficulty validation against topic difficulty
- Admin-only quiz authoring rules

### 3.6 Admin and feedback APIs

Still needed:

- Admin user APIs
- Admin topic APIs
- Admin document APIs
- AI usage reporting
- Feedback entity and API for `LIKE` and `DISLIKE`
- Analytics dashboards/API responses
- ML prediction inspection APIs

### 3.7 Operational hardening

Still recommended:

- Redis-backed rate limiting abstraction and implementation
- Testcontainers coverage for PostgreSQL and pgvector
- Structured request logging with sensitive-field filtering
- Health indicators for Python ML service and AI provider
- Database backup and migration runbook
- Production Dockerfiles and deployment configuration
- CI pipeline for backend, frontend, and ML service
- API documentation with OpenAPI

## 4. Frontend Work Remaining Compared With Backend

This is the largest remaining integration area. The backend exposes real capabilities, but the frontend still uses mock state and mock responses in most places.

### 4.1 Replace mock authentication with JWT authentication

Current frontend behavior:

- `useAuth.js` stores a fake user in LocalStorage.
- Login accepts any values.
- Register accepts any values.
- Logout only clears LocalStorage.

Required frontend work:

- Add `POST /api/auth/register` integration.
- Add `POST /api/auth/login` integration.
- Store access and refresh tokens securely for the chosen browser strategy.
- Add Axios request interceptor for the access token.
- Add refresh-token retry behavior on `401` responses.
- Call `POST /api/auth/logout`.
- Call `GET /api/auth/me` after startup.
- Redirect to login when the refresh flow fails.
- Display backend validation and authentication errors.
- Add real password confirmation validation.
- Add password visibility controls.
- Add forgot-password and email-verification UI when backend flows exist.

### 4.2 Replace local conversations with backend conversations

Current frontend behavior:

- Conversations come from `mockData.js`.
- New conversations use browser-generated IDs.
- Conversations are stored only in LocalStorage.
- Sidebar history is partly hard-coded into `conversationGroups`.
- Search input has no state or filtering behavior.
- Rename, share, archive, and delete menu items are visual only.

Required frontend work:

- Load conversations from `GET /api/conversations`.
- Create conversations through `POST /api/conversations`.
- Load a selected conversation through `GET /api/conversations/{id}`.
- Rename through `PATCH /api/conversations/{id}`.
- Delete through `DELETE /api/conversations/{id}`.
- Replace hard-coded sidebar groups with API data grouped by date.
- Implement dynamic conversation search.
- Add loading, empty, error, and retry states.
- Add optimistic updates only where rollback behavior is defined.
- Add archive/share behavior or clearly remove unavailable actions.

### 4.3 Connect chat to SSE streaming

Current frontend behavior:

- `Chat.jsx` calls `waitForAssistantReply`.
- The response is generated entirely in the browser.
- There is no cancellation or actual stream parsing.
- Regenerate is passed as an empty callback.

Required frontend work:

- Create `src/services/chatApi.js`.
- Send chat requests to `POST /api/chat/stream`.
- Implement an SSE-compatible POST streaming client. Native `EventSource` does not support POST request bodies, so use `fetch` with a readable stream or an SSE client that supports POST.
- Handle `start`, `chunk`, `complete`, and `error` events.
- Append assistant chunks incrementally.
- Add a real stop/cancel mechanism using `AbortController`.
- Persist the completed backend message rather than duplicating it locally.
- Recover cleanly when the SSE connection fails.
- Record frontend streaming state separately from message persistence.

### 4.4 Connect profiles and settings

Current frontend behavior:

- Profile page displays hard-coded Niraj User values.
- Settings page only contains placeholder text.
- Theme selection is not implemented.
- Password change is not implemented.

Required frontend work:

- Load profile through `GET /api/users/me/profile`.
- Update profile through `PATCH /api/users/me/profile`.
- Add fields for display name, bio, profile image URL, preferred language, experience level, and learning goal.
- Add password-change form using `POST /api/auth/change-password`.
- Add real theme modes: light, dark, and system.
- Persist theme and chat preferences.
- Add enter-to-send, timestamps, streaming, history, and compact-mode settings.
- Add loading skeletons and save/error feedback.

### 4.5 Build learning screens missing from the frontend

The current frontend has no dedicated UI for the backend learning APIs.

Required pages/components:

- Topic catalog page using `GET /api/topics`.
- Topic detail page using `GET /api/topics/{slug}`.
- Progress dashboard using `GET /api/progress`.
- Topic progress detail and update flow using `/api/progress/{topicId}`.
- Recommendation panel using `GET /api/recommendations`.
- Skill score, questions attempted, correct answers, and time spent visualizations.
- Empty, loading, and unavailable-ML states.

### 4.6 Build quiz UI

The backend now exposes quiz APIs, but the frontend has no quiz workflow.

Required work:

- Quiz list page using `GET /api/quizzes`.
- Quiz detail page using `GET /api/quizzes/{id}`.
- Question renderer for MCQ and true/false.
- Submission through `POST /api/quizzes/{id}/submit`.
- Result screen showing score and correct count.
- Do not render answer keys before submission.
- Add future-safe placeholders for short-answer and coding questions.
- Add quiz history when the backend endpoint is available.

### 4.7 Build documents and RAG UI

The backend has document ingestion and search APIs, but the current attachment button does not upload files.

Required work:

- Add document upload/ingestion UI.
- Show selected file metadata.
- Connect supported text content to `POST /api/documents`.
- Add document list and source metadata.
- Add search UI using `/api/documents/search?q=...`.
- Show retrieval references in AI responses when the backend exposes them.
- Handle unsupported file types and upload failures.
- Do not upload provider secrets or use browser-side model APIs.

### 4.8 Connect feedback and message actions

Current frontend behavior:

- Copy works locally.
- Like and dislike buttons exist visually but are not connected.
- Regenerate is not implemented.
- More menus are not implemented.

Required work:

- Add backend feedback entity/API first, then connect like/dislike.
- Add regenerate behavior using the last user message and SSE endpoint.
- Add message-level loading and retry states.
- Add share behavior with a defined backend contract.
- Add delete/archive behavior to the conversation API.
- Add toast notifications for successful and failed actions.

### 4.9 Fix frontend architecture mismatches

The frontend plan requests TypeScript types, but the project is JavaScript/JSX.

Choose one direction:

1. Keep JavaScript and add JSDoc typedefs plus runtime validation, or
2. Migrate incrementally to TypeScript and create `chat.ts`, API DTO types, and typed hooks.

For a production application with this many API contracts, TypeScript is recommended, but migration should be incremental rather than a full rewrite.

Also add:

- `src/services/chatApi.js`
- `src/services/authApi.js` or equivalent API module
- `src/context/` for Auth, Chat, and Theme state, or a deliberately documented alternative
- `frontend/.env.example` with `VITE_API_BASE_URL=http://localhost:8080/api`
- Shared API response/error parsing
- Request cancellation and retry helpers

### 4.10 Accessibility and interaction gaps

The UI shell has many good semantic buttons, but remaining work includes:

- Fix nested button structure in the sidebar conversation item and its three-dot trigger.
- Add accessible labels and keyboard behavior to every menu.
- Implement Escape and outside-click behavior for dropdowns/dialogs.
- Add focus restoration after mobile drawer close.
- Add visible validation messages and `aria-invalid` state.
- Add accessible live-region announcements for streaming and errors.
- Respect `prefers-reduced-motion` in all custom animations.

### 4.11 Visual and responsive quality gaps

Still required:

- Implement dark/light/system themes instead of placeholder copy.
- Replace the remaining starter CSS in `App.css` with intentional application styling or remove unused starter rules.
- Verify 320px, 375px, 414px, 768px, 1024px, 1280px, 1440px, and 1920px layouts.
- Add mobile keyboard-safe composer behavior.
- Add code splitting to reduce the main bundle warning.
- Add loading skeletons for sidebar, profile, settings, conversations, and messages.
- Add reusable empty/error states with retry actions.
- Add toast system.

## 5. Frontend API Mapping

| Frontend feature | Backend endpoint | Current frontend state |
|---|---|---|
| Register | `POST /api/auth/register` | Mock only |
| Login | `POST /api/auth/login` | Mock only |
| Current user | `GET /api/auth/me` | Not connected |
| Refresh token | `POST /api/auth/refresh` | Not connected |
| Logout | `POST /api/auth/logout` | LocalStorage only |
| Change password | `POST /api/auth/change-password` | Missing |
| Profile read | `GET /api/users/me/profile` | Hard-coded UI |
| Profile update | `PATCH /api/users/me/profile` | Missing |
| Topics | `GET /api/topics` | Missing |
| Topic detail | `GET /api/topics/{slug}` | Missing |
| Progress list | `GET /api/progress` | Missing |
| Progress update | `PATCH /api/progress/{topicId}` | Missing |
| Conversations | `GET /api/conversations` | Mock/local only |
| Create conversation | `POST /api/conversations` | Local only |
| Conversation detail | `GET /api/conversations/{id}` | Local only |
| Rename conversation | `PATCH /api/conversations/{id}` | Visual placeholder |
| Delete conversation | `DELETE /api/conversations/{id}` | Visual placeholder |
| Send/stream chat | `POST /api/chat/stream` | Browser mock only |
| Quizzes | `GET /api/quizzes` | Missing |
| Quiz detail | `GET /api/quizzes/{id}` | Missing |
| Submit quiz | `POST /api/quizzes/{id}/submit` | Missing |
| Recommendations | `GET /api/recommendations` | Missing |
| Documents | `POST /api/documents` | Attachment placeholder |
| Document search | `GET /api/documents/search?q=` | Missing |
| ML health | Python `/health` via backend boundary | Not a direct frontend concern |

## 6. Recommended Completion Order

### Frontend Phase 1: API foundation

1. Add `frontend/.env.example`.
2. Create Axios client with base URL and JWT interceptors.
3. Replace mock auth with register/login/me/refresh/logout.
4. Add shared API error handling.
5. Add an authenticated app bootstrap/loading state.

### Frontend Phase 2: Real chat

1. Replace local conversation initialization with `GET /api/conversations`.
2. Implement create/select/rename/delete.
3. Add `chatApi.streamMessage()` with `fetch` and `AbortController`.
4. Render SSE chunks incrementally.
5. Connect message copy, edit, regenerate, and error states.

### Frontend Phase 3: User and learning

1. Connect profile and password change.
2. Implement theme and settings persistence.
3. Build topic catalog and progress screens.
4. Add recommendations panel.

### Frontend Phase 4: Quizzes and RAG

1. Build quiz list/detail/submission/result screens.
2. Build document upload and search workflow.
3. Add feedback and message-quality controls.

### Frontend Phase 5: Quality and release

1. Add loading, empty, error, retry, and toast states.
2. Fix accessibility and nested interactive elements.
3. Add responsive verification at all required viewports.
4. Add code splitting.
5. Run build, lint, browser smoke tests, and backend-connected end-to-end tests.

## 7. Recommended Local Run Sequence

### Start the ML service

```powershell
Push-Location ml-service
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
python -m uvicorn app.main:app --reload --port 8000
Pop-Location
```

### Start the backend

Configure PostgreSQL and environment variables first, then:

```powershell
Push-Location backend
.\mvnw.cmd spring-boot:run
Pop-Location
```

Use:

```text
ML_SERVICE_URL=http://localhost:8000
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

### Start the frontend

Create `frontend/.env.local` from the documented API base URL, then:

```powershell
Push-Location frontend
npm install
npm run dev
Pop-Location
```

Recommended frontend environment value:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

## 8. Final Handoff Assessment

The backend and ML service are beyond scaffolding and have working tested contracts. The frontend is a polished mock UI shell, but it is not yet a full client for the backend.

The largest remaining implementation effort is frontend integration and feature screens, not backend initialization:

- Authentication must move from fake LocalStorage state to JWT APIs.
- Chat must move from mock replies to backend SSE.
- Conversations must move from local mock data to REST persistence.
- Profile, topics, progress, recommendations, quizzes, and documents need UI workflows.
- Settings, themes, errors, loading states, toasts, accessibility, and responsive verification need completion.

Once those frontend phases are complete, the remaining release work is environment setup, PostgreSQL/pgvector verification, real AI provider configuration, and end-to-end testing.
