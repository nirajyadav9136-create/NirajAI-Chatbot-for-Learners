export const initialConversations = [
  {
    id: 'conv-1',
    title: 'Explain Spring Boot',
    createdAt: '2026-09-22T09:00:00.000Z',
    updatedAt: '2026-09-22T09:32:00.000Z',
    messages: [
      {
        id: 'm-1',
        role: 'user',
        content: 'Explain Spring Boot in simple terms and show the typical architecture.',
      },
      {
        id: 'm-2',
        role: 'assistant',
        content:
          'Spring Boot is a framework that lets you build Java applications with less boilerplate. Instead of manually wiring dependencies and configuration, it gives you sensible defaults and auto-configuration.\n\nTypical architecture:\n\n- Controller layer for HTTP endpoints\n- Service layer for business logic\n- Repository layer for database access\n- Entity models for data representation\n- Configuration classes and application properties\n\nThis keeps projects small, fast to start, and easy to maintain.',
      },
    ],
  },
  {
    id: 'conv-2',
    title: 'React project architecture',
    createdAt: '2026-09-21T15:00:00.000Z',
    updatedAt: '2026-09-21T15:45:00.000Z',
    messages: [
      {
        id: 'm-3',
        role: 'user',
        content: 'How should I structure a React project for a growing product?',
      },
      {
        id: 'm-4',
        role: 'assistant',
        content:
          'A scalable React structure usually separates concerns by feature, shared components, and state. Common patterns are:\n\n```tsx\nsrc/\n  app/\n  features/\n  components/\n  hooks/\n  services/\n  utils/\n```\n\nKeep UI primitives reusable, colocate feature logic with the feature, and centralize API calls in one service layer. This makes the app easier to navigate as it grows.',
      },
    ],
  },
  {
    id: 'conv-3',
    title: 'Python ML help',
    createdAt: '2026-09-20T11:15:00.000Z',
    updatedAt: '2026-09-20T11:55:00.000Z',
    messages: [
      {
        id: 'm-5',
        role: 'user',
        content: 'Help me clean a dataset and build a simple machine learning pipeline.',
      },
      {
        id: 'm-6',
        role: 'assistant',
        content:
          'Start by checking missing values, duplicates, and outliers. Then split the data, encode categorical data, and create a baseline model such as LogisticRegression or RandomForest.\n\nA simple flow looks like this:\n\n1. Load the dataset\n2. Clean and transform columns\n3. Train/test split\n4. Fit a model\n5. Evaluate metrics like accuracy and F1 score',
      },
    ],
  },
  {
    id: 'conv-4',
    title: 'PostgreSQL query',
    createdAt: '2026-09-18T12:00:00.000Z',
    updatedAt: '2026-09-18T12:27:00.000Z',
    messages: [
      {
        id: 'm-7',
        role: 'user',
        content: 'Write a PostgreSQL query to find the top 10 customers by total spend.',
      },
      {
        id: 'm-8',
        role: 'assistant',
        content:
          '```sql\nSELECT customer_id, SUM(amount) AS total_spend\nFROM orders\nGROUP BY customer_id\nORDER BY total_spend DESC\nLIMIT 10;\n```\n\nThis groups rows by customer and sorts by the total amount spent in descending order.',
      },
    ],
  },
  {
    id: 'conv-5',
    title: 'Java interview preparation',
    createdAt: '2026-09-16T08:40:00.000Z',
    updatedAt: '2026-09-16T08:50:00.000Z',
    messages: [
      {
        id: 'm-9',
        role: 'user',
        content: 'Give me the most common Java interview questions for backend roles.',
      },
      {
        id: 'm-10',
        role: 'assistant',
        content:
          'Focus on Java fundamentals, OOP principles, collections, multithreading, JVM internals, and design patterns. Common questions include: Java memory model, HashMap vs TreeMap, equals/hashCode, thread safety, and dependency injection.',
      },
    ],
  },
];

export const promptSuggestions = [
  'Explain a complex topic',
  'Help me write code',
  'Analyze a document',
  'Brainstorm ideas',
];
