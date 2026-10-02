# NirajAI Backend and Frontend Integration Plan

## 1. Objective

Connect the existing React frontend to the existing Spring Boot API, preserve the current visual shell where practical, and add the missing frontend workflows represented by the backend API. The ML service remains an internal backend dependency; the browser should call Spring Boot only.

## 2. Current Findings

### Backend runtime and security

- Spring Boot serves the API on its default port, `8080`, under `/api`.
- `SecurityConfig` already enables CORS and allows the configured origin from `app.cors.allowed-origins`.
- The default CORS origin is `http://localhost:5173`, matching Vite.
- Allowed methods include `GET`, `POST`, `PATCH`, `DELETE`, and `OPTIONS`.
- Allowed headers include `Authorization`, `Content-Type`, `Accept`, and `Last-Event-ID`.
- Authentication is stateless JWT. Access tokens last 15 minutes by default; refresh tokens last 30 days.
- `POST /api/chat/stream` returns server-sent events and requires a JSON body containing a UUID `conversationId` and nonblank `content`.
- The backend talks to the ML service through `ML_SERVICE_URL`; the browser must not call the ML service directly.

### Existing frontend state

- `src/hooks/useAuth.js` contains demo-only local storage authentication.
- `src/services/api.js` generates delayed mock assistant replies.
- `src/pages/Chat.jsx` stores mock conversations in local storage and does not use backend conversations or SSE.
- `Profile.jsx` and `Settings.jsx` render hard-coded values/placeholders.
- The router has only login, register, chat, profile, and settings routes.
- No frontend API base URL or Vite proxy is configured.

## 3. Backend Contract Inventory

All paths below are relative to `VITE_API_BASE_URL`, which will default to `http://localhost:8080/api`.

| Method and path | Auth | Frontend use |
|---|---|---|
| `POST /auth/register` | Public | Register with `email`, `username`, `password`; stores returned access/refresh tokens. |
| `POST /auth/login` | Public | Login with `email`, `password`; stores returned tokens. |
| `POST /auth/refresh` | Public | Replace an expired access token using `refreshToken`. |
| `POST /auth/logout` | Public | Revoke the stored refresh token. |
| `GET /auth/me` | Access token | Restore the current user on app startup. |
| `POST /auth/change-password` | Access token | Settings security form. |
| `GET /conversations` | Access token | Load the signed-in user’s conversation list. |
| `POST /conversations` | Access token | Create a conversation with `title`, optional `learningMode`, `topicId`, and `model`. |
| `GET /conversations/{id}` | Access token | Load one conversation and its ordered messages. |
| `PATCH /conversations/{id}` | Access token | Rename a conversation with `title`. |
| `DELETE /conversations/{id}` | Access token | Delete a conversation. |
| `POST /conversations/{id}/messages` | Access token | Optional direct user-message persistence; normal chat uses the stream endpoint. |
| `POST /chat/stream` | Access token | Stream assistant response. Parse `start`, `chunk`, `complete`, and `error` SSE events. |
| `GET /users/me/profile` | Access token | Load profile. |
| `PATCH /users/me/profile` | Access token | Save profile fields. |
| `GET /topics` and `GET /topics/{slug}` | Authenticated by current security rules | Topic catalog and topic detail. |
| `GET /progress` and `GET /progress/{topicId}` | Access token | Progress dashboard/detail. |
| `PATCH /progress/{topicId}` | Access token | Record attempts, correct answers, and time spent. |
| `GET /recommendations` | Access token | Recommendations panel/dashboard. |
| `GET /quizzes`, `GET /quizzes/{id}` | Authenticated by current security rules | Quiz catalog and attempt screen. |
| `POST /quizzes/{id}/submit` | Access token | Submit answer map and show score result. |
| `POST /documents`, `GET /documents`, `GET /documents/search?q=...` | Authenticated by current security rules | Document ingestion/list/search workflow. |
| `GET /actuator/health` | Public | Optional connection/health indicator. |

Important response shapes include `AuthResponse` (`accessToken`, `refreshToken`, `tokenType`, `userId`, `username`, `role`), `UserResponse` (`id`, `email`, `username`, `role`), conversation messages with enum roles `USER`/`ASSISTANT`, profile enums `experienceLevel` and `learningGoal`, and quiz question `options` as a backend string.

## 4. Implementation Work Plan

### Phase 1: API foundation and CORS connectivity

1. Add `frontend/.env.example` documenting `VITE_API_BASE_URL=http://localhost:8080/api`.
2. Add a shared Axios client with `baseURL`, JSON defaults, access-token injection, one-at-a-time refresh handling, and redirect/logout behavior after refresh failure.
3. Add a small API error normalizer so backend `ApiError` messages are shown to users.
4. Keep CORS configuration property-driven and verify the frontend origin is accepted. Improve origin parsing only if needed for whitespace or multiple configured origins.
5. Add a Vite development proxy only if direct CORS testing is inconvenient; the explicit API base URL remains the source of truth.

### Phase 2: Real authentication

1. Replace demo `useAuth` login/register/logout with API calls.
2. Store tokens separately from the user object and restore the session with `GET /auth/me` on startup.
3. Add an authentication bootstrap/loading state so protected routes do not redirect before session restoration finishes.
4. Update login and registration forms to submit the real backend fields. Registration must collect `username`, not only display name.
5. Connect logout to `POST /auth/logout` and clear local state even if the request fails.

### Phase 3: Persisted conversations and SSE chat

1. Replace mock conversation initialization with `GET /conversations`.
2. Create backend conversations before sending the first message; keep UUIDs in the URL.
3. Connect rename and delete actions to their REST endpoints and update the sidebar from server state.
4. Implement `fetch`-based SSE streaming because the browser `EventSource` API cannot send the required POST JSON body or bearer header.
5. Parse named SSE events incrementally, append chunks to one assistant message, persist server-returned conversation data after completion, and show stream errors.
6. Preserve copy/edit UI behavior, but make edit/regenerate use the backend contract and avoid duplicate local assistant replies.
7. Remove mock conversation persistence and mock reply generation from the active chat path.

### Phase 4: User and learning pages

1. Replace the static profile page with `GET/PATCH /users/me/profile` and `GET /auth/me` data.
2. Add change-password controls to settings using `POST /auth/change-password`.
3. Add real settings persistence for client-only preferences such as theme, while clearly separating them from backend security settings.
4. Add routes/pages for topics, progress, and recommendations using the corresponding API endpoints.

### Phase 5: Quiz and document pages

1. Add a quiz catalog/detail route, answer controls for MCQ and true/false questions, submit handling, and score/result state.
2. Add a document workspace for title/source/content ingestion, document listing, and search results.
3. Link topics where IDs are available and handle empty/loading/error states.

### Phase 6: Verification and cleanup

1. Run the frontend lint and production build.
2. Run the backend Maven test suite.
3. Run ML-service tests if the Python environment is available.
4. Exercise a connected flow: register, refresh/session restore, create conversation, stream chat, reload conversation, update profile, change password, and logout.
5. Verify CORS preflight and authenticated requests from `http://localhost:5173`.
6. Check mobile layouts, keyboard submission, loading/empty/error states, and browser console errors.

## 5. Planned Frontend Surface

Existing files to update: `App.jsx`, `hooks/useAuth.js`, `services/api.js`, `pages/Login.jsx`, `pages/Register.jsx`, `pages/Chat.jsx`, `pages/Profile.jsx`, `pages/Settings.jsx`, and relevant sidebar/message components.

New shared files: API client/auth token helpers and frontend environment example.

New route-level pages: topics, progress/recommendations, quizzes, and documents. These are required because the backend already exposes those user-facing capabilities and the current frontend has no corresponding route.

## 6. Risks and Decisions

- Access tokens are kept in browser storage to match the existing client-only architecture; the API layer must minimize token duplication and refresh races.
- The backend SSE endpoint uses POST and bearer authentication, so streaming will use `fetch` and an SSE parser rather than native `EventSource`.
- Backend document and quiz list/create endpoints are authenticated by the global security rule even though their controllers do not accept `Authentication`; the frontend will still send the bearer token.
- The backend’s quiz `options` field is a string. The quiz UI will parse JSON when possible and fall back to newline/comma choices without changing the backend contract.
- Existing unrelated worktree changes must remain untouched.

## 7. Validation Gates

- Gate A: the plan exists before source-code execution.
- Gate B: API client and auth compile/lint, and backend tests remain green.
- Gate C: chat stream can render at least one `chunk` event and handles `error`.
- Gate D: every new route has a reachable navigation entry and a loading/error/empty state.
- Gate E: production build succeeds and a connected browser smoke flow completes.
