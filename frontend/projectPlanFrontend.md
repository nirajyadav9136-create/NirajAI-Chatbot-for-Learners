You are an expert senior frontend engineer and UI/UX designer.

I am building a full-stack AI chatbot application called "NirajAI".

Your task is to build a complete, production-quality frontend that closely recreates the modern ChatGPT-style conversational AI experience and layout, while using ORIGINAL branding, icons, assets, and code. Do not copy proprietary ChatGPT source code, logos, illustrations, or assets.

IMPORTANT:
- Do not give me explanations first.
- Inspect the existing project structure before modifying anything.
- If the project is empty, initialize the required structure.
- Implement the frontend directly in the project.
- Do not create a mockup only.
- Build reusable React components.
- Make all major UI interactions functional on the frontend even if the backend is not connected yet.
- Use clean, maintainable, production-quality code.
- Do not put everything into App.jsx.
- Separate components logically.
- Do not use inline SVGs copied from ChatGPT.
- Use Lucide React icons or another appropriate open-source icon library.
- Do not use copyrighted ChatGPT assets.
- Use the application name "NirajAI" throughout the UI.

==================================================
1. TECHNOLOGY STACK
==================================================

Use:

- React
- JavaScript
- Vite
- Tailwind CSS
- React Router
- Axios
- Lucide React
- React Markdown
- react-syntax-highlighter
- clsx
- tailwind-merge

If the project already has compatible versions installed, do not unnecessarily replace them.

Use functional React components and React hooks.

Use TypeScript interfaces/types for all important data structures.

==================================================
2. DESIGN GOAL
==================================================

Create a polished AI chatbot interface inspired by modern conversational AI applications.

The UI should have:

DESKTOP:
- Left sidebar
- Main chat area
- Top navigation/header
- Centered conversation content
- Bottom message composer

MOBILE:
- Collapsible sidebar
- Full-width chat
- Mobile header
- Responsive message composer

The interface must feel:
- Minimal
- Modern
- Professional
- Spacious
- Fast
- Accessible
- AI-product oriented

Use subtle borders, rounded elements, proper spacing, hover states, transitions, and excellent typography.

Do NOT make it look like a generic dashboard.

==================================================
3. APPLICATION STRUCTURE
==================================================

Create this structure if appropriate:

src/
├── assets/
├── components/
│   ├── layout/
│   │   ├── AppLayout.tsx
│   │   ├── Sidebar.tsx
│   │   ├── MobileHeader.tsx
│   │   └── TopBar.tsx
│   │
│   ├── chat/
│   │   ├── ChatWindow.tsx
│   │   ├── ChatHeader.tsx
│   │   ├── MessageList.tsx
│   │   ├── UserMessage.tsx
│   │   ├── AssistantMessage.tsx
│   │   ├── MessageActions.tsx
│   │   ├── TypingIndicator.tsx
│   │   ├── WelcomeScreen.tsx
│   │   └── PromptSuggestions.tsx
│   │
│   ├── composer/
│   │   ├── MessageComposer.tsx
│   │   ├── AttachmentButton.tsx
│   │   ├── ModelSelector.tsx
│   │   └── VoiceButton.tsx
│   │
│   ├── dialogs/
│   │   ├── SettingsModal.tsx
│   │   ├── ProfileModal.tsx
│   │   ├── ShareChatModal.tsx
│   │   └── DeleteChatModal.tsx
│   │
│   └── common/
│       ├── Button.tsx
│       ├── Avatar.tsx
│       ├── Dropdown.tsx
│       ├── Tooltip.tsx
│       └── LoadingSpinner.tsx
│
├── pages/
│   ├── Login.tsx
│   ├── Register.tsx
│   ├── Chat.tsx
│   ├── Settings.tsx
│   └── Profile.tsx
│
├── hooks/
│   ├── useChat.ts
│   ├── useSidebar.ts
│   └── useTheme.ts
│
├── services/
│   └── api.ts
│
├── types/
│   └── chat.ts
│
├── data/
│   └── mockData.ts
│
├── App.tsx
├── main.tsx
└── index.css

Adapt this structure if the existing project uses a different organization.

==================================================
4. ROUTES
==================================================

Implement React Router routes:

/login
/register
/chat
/chat/:conversationId
/settings
/profile

The main application should use:

/chat

as the primary route.

If the user is not authenticated, redirect to /login.

For now, create a simple frontend authentication state/localStorage mock so the UI can be tested without a backend.

Structure the authentication code so that it can later be replaced with JWT authentication from Spring Boot.

==================================================
5. MAIN CHAT LAYOUT
==================================================

Build the primary interface like this:

--------------------------------------------------
| SIDEBAR |             MAIN CHAT                |
|          |                                     |
| + New    | Top Header                          |
| Chat     |                                     |
|          |                                     |
| Search   |                                     |
| chats    | Conversation                        |
|          |                                     |
| Today    | User message                        |
| Chat 1   |                                     |
| Chat 2   | AI response                         |
| Chat 3   |                                     |
|          |                                     |
| Previous |                                     |
| Chat 4   |                                     |
|          |                                     |
|          |                                     |
| Settings | Message Composer                    |
| Profile  |                                     |
--------------------------------------------------

==================================================
6. SIDEBAR
==================================================

Create a collapsible sidebar.

Desktop:
- Width approximately 260px
- Fixed height
- Full viewport height

Mobile:
- Sidebar becomes an overlay/drawer
- Add backdrop
- Close when clicking outside

Sidebar contents:

TOP:
- NirajAI logo/name
- New Chat button
- Search conversations

CONVERSATION HISTORY:

Today
- Explain Spring Boot
- React project architecture
- Python ML help

Yesterday
- PostgreSQL query
- Java interview preparation

Previous 7 days
- AI agent architecture
- RAG implementation

Each conversation item should:
- Show title
- Highlight active conversation
- Have hover state
- Have three-dot menu

Three-dot menu:
- Rename
- Share
- Archive
- Delete

BOTTOM:
- User avatar
- User name
- Settings
- Upgrade/Plan section placeholder
- Logout

The sidebar should scroll independently if there are many conversations.

==================================================
7. NEW CHAT
==================================================

Clicking "New Chat" must:

- Create a new local conversation
- Clear current messages
- Show WelcomeScreen
- Focus the message input
- Add conversation to history after first message

Use mock state for now.

Create:

Conversation interface:

id
title
messages
createdAt
updatedAt

==================================================
8. WELCOME SCREEN
==================================================

When no messages exist, show a beautiful centered welcome screen.

Example:

NirajAI

How can I help you today?

Below it show prompt suggestion cards:

"Explain a complex topic"
"Help me write code"
"Analyze a document"
"Brainstorm ideas"

Clicking a suggestion should populate/send that prompt.

Do not use a huge marketing hero section.

Keep the experience focused on starting a conversation.

==================================================
9. CHAT MESSAGES
==================================================

User messages:
- Clearly distinguish them from AI messages
- Use proper spacing
- Preserve multiline text
- Support code snippets

Assistant messages:
- Markdown rendering
- Headings
- Lists
- Tables
- Links
- Blockquotes
- Inline code
- Code blocks

Code blocks must include:
- Language label
- Copy button
- Syntax highlighting

Example:

```java
public class Example {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
````

The code block must look polished and readable.

==================================================
10. MESSAGE ACTIONS
===================

For assistant responses implement:

* Copy
* Regenerate
* Like
* Dislike
* More

For user messages:

* Edit
* Copy
* More

Actions should appear subtly and become visible on hover/focus.

Copy button should actually copy the content to clipboard.

Show a temporary "Copied" state.

==================================================
11. MESSAGE EDITING
===================

When the user clicks Edit:

* Convert the message into an editable textarea
* Allow Cancel
* Allow Save
* After saving, simulate regeneration of the assistant response

Do not reload the page.

==================================================
12. MESSAGE COMPOSER
====================

Create a highly polished bottom composer.

Structure:

┌──────────────────────────────────────────────┐
│ +   Message NirajAI...                     🎤 │
│                                              │
│                              Model     Send ➤ │
└──────────────────────────────────────────────┘

Features:

* Multiline textarea
* Auto-grow
* Enter = send
* Shift + Enter = newline
* Send button
* Stop generation button while AI is responding
* Attachment button
* Voice button
* Model selector

Disable Send when message is empty.

Composer should remain near the bottom of the viewport.

On mobile it must adapt properly.

==================================================
13. MODEL SELECTOR
==================

Create a model dropdown.

Example:

NirajAI
Models:

* Gemini
* GPT
* Claude
* Local Model

Do not connect these APIs yet.

Store selected model in frontend state.

Design the dropdown professionally.

==================================================
14. ATTACHMENTS
===============

Allow users to select:

* PDF
* DOCX
* TXT
* CSV
* Images

For now:

* Display selected files above/below composer
* Show filename
* Show file type
* Show file size
* Allow removing attachment

Do not actually upload to backend yet.

Create the frontend architecture so an API upload service can later be added.

==================================================
15. AI RESPONSE SIMULATION
==========================

Until the backend is connected, implement mock AI responses.

When the user sends:

1. Add user message
2. Show typing indicator
3. Wait briefly
4. Stream a mock assistant response character-by-character or chunk-by-chunk
5. Finish response
6. Add message actions

The UI must visually behave like a real streaming AI application.

Use async functions and React state.

Create:

useChat()

hook that handles:

sendMessage()
stopGeneration()
regenerateMessage()
editMessage()
deleteMessage()
clearChat()

==================================================
16. STREAMING ARCHITECTURE
==========================

Prepare the frontend for future Spring Boot SSE streaming.

Create a service abstraction:

chatApi.sendMessage()

and:

chatApi.streamMessage()

Do NOT tightly couple the UI to mock responses.

Eventually the backend will expose something like:

POST /api/chat

POST /api/chat/stream

GET /api/conversations

GET /api/conversations/:id

DELETE /api/conversations/:id

POST /api/messages

Design the frontend service layer around these future endpoints.

==================================================
17. TOP HEADER
==============

Create a minimal top header.

Left:

* Mobile menu button
* Current conversation title

Center/right:

* Model selector
* Share button
* More menu

Do not make the header oversized.

==================================================
18. SEARCH
==========

Implement conversation search.

When the user searches:

"java"

show conversations matching:

* Java interview
* Java Spring Boot
* Java Streams

Search should update dynamically.

Use local mock data for now.

==================================================
19. SETTINGS
============

Create a settings interface.

Sections:

General

* Theme
* Language
* Compact mode

Appearance

* Light
* Dark
* System

Chat

* Enter to send
* Show timestamps
* Streaming responses

Privacy

* Chat history
* Data controls

About

* NirajAI version

==================================================
20. THEME SYSTEM
================

Implement:

Light mode
Dark mode
System mode

Use Tailwind dark mode.

Persist selected theme to localStorage.

Avoid excessive pure-black/pure-white surfaces.

Use subtle neutral backgrounds and borders.

Make sure text contrast is accessible.

==================================================
21. PROFILE
===========

Create profile page/modal.

Show:

* Avatar
* Name
* Email
* Account creation date

Actions:

* Edit profile
* Change password
* Logout

These can be mocked for now.

==================================================
22. LOGIN PAGE
==============

Create a professional login page.

Fields:

Email
Password

Buttons:

Login
Continue with Google placeholder

Links:

Forgot password
Create account

Do not implement OAuth yet.

Use frontend validation.

==================================================
23. REGISTER PAGE
=================

Fields:

Name
Email
Password
Confirm Password

Implement:

* Required validation
* Email validation
* Password length validation
* Password confirmation validation
* Password visibility toggle

After successful mock registration redirect to /chat.

==================================================
24. RESPONSIVENESS
==================

The application must work at:

320px
375px
414px
768px
1024px
1280px
1440px
1920px

Do not simply shrink desktop.

Design proper mobile layouts.

Mobile:

* Sidebar drawer
* Compact header
* Full-width composer
* Proper keyboard-safe spacing
* Touch-friendly buttons

Tablet:

* Collapsible sidebar

Desktop:

* Persistent sidebar

==================================================
25. ACCESSIBILITY
=================

Implement:

* Semantic HTML
* aria-labels
* Keyboard navigation
* Focus states
* Screen reader-friendly buttons
* Proper button elements
* Accessible dialogs
* Escape to close dialogs
* Visible focus indicators

Do not use divs as buttons.

==================================================
26. ANIMATIONS
==============

Use subtle animations only.

Implement:

* Sidebar open/close
* Modal open/close
* Typing indicator
* Message appearance
* Button hover
* Dropdown animation
* Toast notifications

Avoid excessive animations.

Use CSS/Tailwind transitions.

Respect:

prefers-reduced-motion

==================================================
27. TOAST SYSTEM
================

Implement toast notifications for:

* Message copied
* Conversation renamed
* Conversation deleted
* Chat shared
* Settings saved
* File attached
* Logout

If a toast library is already installed, use it.

Otherwise create a lightweight reusable toast component.

==================================================
28. DATA TYPES
==============

Create TypeScript types:

type MessageRole =
| "user"
| "assistant"
| "system";

interface Message {
id: string;
conversationId: string;
role: MessageRole;
content: string;
createdAt: string;
isStreaming?: boolean;
}

interface Conversation {
id: string;
title: string;
messages: Message[];
createdAt: string;
updatedAt: string;
}

interface User {
id: string;
name: string;
email: string;
avatar?: string;
}

interface AIModel {
id: string;
name: string;
provider: string;
}

==================================================
29. STATE MANAGEMENT
====================

For now use React Context or a lightweight state-management approach.

Create:

AuthContext
ChatContext
ThemeContext

Do not introduce Redux unless the existing project already uses it.

Persist:

* Theme
* User session
* Conversations
* Selected model

to localStorage for development.

Keep the architecture easy to replace with backend APIs later.

==================================================
30. API SERVICE
===============

Create:

src/services/api.ts

and:

src/services/chatApi.ts

Use Axios.

Configure:

const API_BASE_URL =
import.meta.env.VITE_API_BASE_URL ||
"[http://localhost:8080/api](http://localhost:8080/api)";

Prepare Axios interceptors for JWT:

Authorization: Bearer <token>

Do not hard-code API keys anywhere in the frontend.

==================================================
31. ENVIRONMENT VARIABLES
=========================

Create:

.env.example

with:

VITE_API_BASE_URL=[http://localhost:8080/api](http://localhost:8080/api)

Never place Gemini/OpenAI API keys in frontend environment variables.

AI provider API keys must eventually remain in the Spring Boot backend.

==================================================
32. DESIGN DETAILS
==================

Use a clean neutral color system.

Light:

background:
very light neutral

sidebar:
slightly darker neutral

cards:
white/light neutral

borders:
subtle neutral

text:
dark neutral

Dark:

background:
dark neutral

sidebar:
slightly darker neutral

cards:
dark neutral

borders:
subtle dark neutral

text:
light neutral

Use one restrained accent color for important interactive elements.

Do not make the interface overly colorful.

Typography should be modern and readable.

Use a system font stack unless an existing font system is already configured.

==================================================
33. CHAT WIDTH
==============

Messages should not span the entire screen on large monitors.

Use a centered conversation container with an appropriate max width.

The composer should align with the conversation content.

On very large displays, preserve comfortable reading width.

==================================================
34. EMPTY STATES
================

Create polished empty states for:

* No conversations
* No search results
* No attachments
* No messages

Each should provide a useful action.

==================================================
35. ERROR STATES
================

Create reusable error UI.

Examples:

AI unavailable

Network error

Authentication expired

File upload failed

Conversation failed to load

Each should provide:

* Clear message
* Retry button

==================================================
36. LOADING STATES
==================

Implement skeleton/loading states for:

* Conversation history
* Messages
* User profile
* Settings

Do not show blank screens while loading.

==================================================
37. SECURITY FRONTEND RULES
===========================

Never:

* Store OpenAI API keys
* Store Gemini API keys
* Expose provider secrets
* Trust user-generated HTML
* Use dangerouslySetInnerHTML unless absolutely necessary and sanitized

Markdown rendering must be safely handled.

==================================================
38. CODE QUALITY
================

Follow:

* SOLID principles where appropriate
* DRY
* Reusable components
* Clear naming
* Small components
* No unnecessary duplication
* No giant components
* No unused imports
* No console errors
* No TypeScript errors

Avoid:

* any
* hard-coded repeated values
* deeply nested conditional JSX
* unnecessary useEffect
* prop drilling where Context is appropriate

==================================================
39. MOCK DATA
=============

Create realistic mock conversations.

Example:

Conversation:

"Explain Spring Boot"

User:
What is Spring Boot?

Assistant:
Spring Boot simplifies Spring application development...

Conversation:

"React Authentication"

User:
How should I implement JWT authentication?

Assistant:
JWT authentication typically consists of...

Use enough messages to demonstrate scrolling and history.

==================================================
40. FINAL QUALITY REQUIREMENTS
==============================

Before considering the task complete:

1. Run npm install if required.
2. Run the development server.
3. Fix all JavaScript errors.
4. Fix all ESLint errors if ESLint is configured.
5. Fix all console errors.
6. Test routing.
7. Test responsive layout.
8. Test sidebar.
9. Test new chat.
10. Test sending messages.
11. Test mock streaming.
12. Test copy message.
13. Test edit message.
14. Test regenerate.
15. Test conversation search.
16. Test rename.
17. Test delete.
18. Test settings.
19. Test dark/light mode.
20. Test login/register.
21. Test mobile layout.
22. Verify no API secrets exist in frontend.
23. Verify npm run build succeeds.

==================================================
41. IMPORTANT IMPLEMENTATION RULE
=================================

Build the application incrementally but complete the entire frontend.

Do not stop after creating only the homepage.

Do not return pseudo-code.

Do not give me snippets that I must manually assemble.

Create the actual files and code in the project.

If an existing file conflicts with the architecture, modify it rather than creating duplicate implementations.

Preserve useful existing code.

==================================================
42. FINAL EXPECTED RESULT
=========================

When finished, I should be able to run:

npm install

npm run dev

and get a polished NirajAI conversational AI application with:

* ChatGPT-style layout
* Sidebar
* New Chat
* Conversation history
* Search
* User/AI messages
* Markdown
* Code blocks
* Copy
* Regenerate
* Edit
* Delete
* File attachments UI
* Model selector
* Voice button UI
* Streaming simulation
* Responsive design
* Dark/light/system theme
* Login
* Register
* Settings
* Profile
* Toasts
* Loading states
* Error states
* Local persistence
* Clean TypeScript architecture
* Backend-ready API service layer

The application should look and feel like a polished modern AI chat product, not a student dashboard or generic CRUD application.

After implementation, show me:

1. Files created/modified
2. npm packages installed
3. Commands required to run the project
4. Any remaining TODOs
5. Any assumptions made

Do not implement backend functionality yet.
Focus entirely on the frontend and make it ready to connect to my Spring Boot backend later.

````

### How I recommend using this in VS Code

Because this is a **large implementation**, don't put it into a normal inline autocomplete prompt. Open **GitHub Copilot Chat → Agent mode**, paste the master prompt, and let Copilot inspect and modify the project.

Before doing it, your project should ideally be initialized as:

```text
nira-ai/
├── package.json
├── vite.config.ts
├── tsconfig.json
├── src/
└── ...
````

Then after Copilot finishes:

```bash
npm install
npm run dev
```

For your particular project, I would **not connect the AI API yet**. First get this frontend working perfectly with mock responses. Then we can build the second layer:

```text
React/JavaScript
       ↓
Axios
       ↓
Spring Boot
       ↓
Spring AI
       ↓
Gemini / OpenAI
       ↓
SSE streaming
       ↓
React Chat UI
```

That will give you a much cleaner architecture for the ChatGPT clone you're planning.
