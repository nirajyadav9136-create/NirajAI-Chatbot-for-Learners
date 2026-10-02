import AssistantMessage from './AssistantMessage';
import UserMessage from './UserMessage';
import TypingIndicator from './TypingIndicator';

export default function MessageList({ messages, isTyping, onCopy, onRegenerate, onSaveEdit, onCancelEdit }) {
  return (
    <div className="flex-1 overflow-y-auto px-4 pb-4 pt-6 md:px-6">
      {messages.length === 0 ? null : (
        <div className="mx-auto max-w-5xl">
          {messages.map((message) =>
            message.role === 'user' ? (
              <UserMessage
                key={message.id}
                message={message}
                onCopy={onCopy}
                onSaveEdit={onSaveEdit}
                onCancelEdit={onCancelEdit}
              />
            ) : (
              <AssistantMessage
                key={message.id}
                message={message}
                onCopy={onCopy}
                onRegenerate={onRegenerate}
              />
            ),
          )}

          {isTyping && <TypingIndicator />}
        </div>
      )}
    </div>
  );
}
