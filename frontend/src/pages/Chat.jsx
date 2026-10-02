import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { promptSuggestions } from '../data/mockData';
import { createConversation, deleteConversation, getConversation, listConversations, normalizeApiError, renameConversation, streamChatMessage } from '../services/api';
import { useAuth } from '../hooks/useAuth';
import TopBar from '../components/layout/TopBar';
import MessageList from '../components/chat/MessageList';
import MessageComposer from '../components/composer/MessageComposer';
import WelcomeScreen from '../components/chat/WelcomeScreen';
import AppLayout from '../components/layout/AppLayout';

const createMessage = (role, content) => ({
  id: `${role}-${Date.now()}-${Math.random().toString(16).slice(2)}`,
  role,
  content,
});

export default function ChatPage() {
  const navigate = useNavigate();
  const { conversationId } = useParams();
  const { logout } = useAuth();
  const [conversations, setConversations] = useState([]);
  const [activeConversationId, setActiveConversationId] = useState(conversationId || null);
  const [isTyping, setIsTyping] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    listConversations()
      .then((loadedConversations) => {
        setConversations(loadedConversations);
        if (!conversationId && loadedConversations[0]) {
          setActiveConversationId(loadedConversations[0].id);
          navigate(`/chat/${loadedConversations[0].id}`, { replace: true });
        }
      })
      .catch((requestError) => setError(normalizeApiError(requestError)));
  }, [conversationId, navigate]);

  useEffect(() => {
    if (!conversationId) {
      navigate(`/chat/${activeConversationId}`, { replace: true });
      return;
    }

    const exists = conversations.some((conversation) => conversation.id === conversationId);
    if (!exists && conversations.length > 0) {
      navigate(`/chat/${conversations[0].id}`, { replace: true });
    }
  }, [conversationId, conversations, navigate, activeConversationId]);

  useEffect(() => {
    if (!conversationId || activeConversationId !== conversationId) return;
    getConversation(conversationId)
      .then((conversation) => setConversations((current) => current.map((item) => item.id === conversation.id ? conversation : item)))
      .catch((requestError) => setError(normalizeApiError(requestError)));
  }, [conversationId, activeConversationId]);

  const activeConversation = useMemo(
    () => conversations.find((conversation) => conversation.id === (conversationId || activeConversationId)) || conversations[0],
    [conversations, conversationId, activeConversationId],
  );

  const updateConversation = (id, updater) => {
    setConversations((current) =>
      current.map((conversation) =>
        conversation.id === id
          ? { ...conversation, ...updater(conversation), updatedAt: new Date().toISOString() }
          : conversation,
      ),
    );
  };

  const handleNewChat = () => {
    createConversation({ title: 'New Chat', model: 'local-tutor' })
      .then((newConversation) => {
        setConversations((current) => [newConversation, ...current]);
        setActiveConversationId(newConversation.id);
        navigate(`/chat/${newConversation.id}`);
      })
      .catch((requestError) => setError(normalizeApiError(requestError)));
  };

  const handleSelectConversation = (id) => {
    setActiveConversationId(id);
    navigate(`/chat/${id}`);
  };

  const handleRenameConversation = async (id, title) => {
    try {
      const updated = await renameConversation(id, title);
      setConversations((current) => current.map((conversation) => conversation.id === id ? { ...conversation, ...updated } : conversation));
    } catch (requestError) {
      setError(normalizeApiError(requestError));
    }
  };

  const handleDeleteConversation = async (id) => {
    try {
      await deleteConversation(id);
      const remaining = conversations.filter((conversation) => conversation.id !== id);
      setConversations(remaining);
      if (id === activeConversationId) {
        if (remaining[0]) navigate(`/chat/${remaining[0].id}`);
        else handleNewChat();
      }
    } catch (requestError) {
      setError(normalizeApiError(requestError));
    }
  };

  const appendUserMessage = async (content) => {
    if (!activeConversation) return;

    const userMessage = createMessage('user', content);
    updateConversation(activeConversation.id, (conversation) => ({
      messages: [...conversation.messages, userMessage],
      title: conversation.messages.length === 0 ? content.slice(0, 30) || 'New Chat' : conversation.title,
    }));

    setIsTyping(true);
    setError('');
    let assistantMessageId;
    try {
      let streamedContent = '';
      await streamChatMessage(activeConversation.id, content, ({ event, data }) => {
        if (event === 'chunk') {
          streamedContent = streamedContent ? `${streamedContent} ${data}` : data;
          if (!assistantMessageId) assistantMessageId = createMessage('assistant', '').id;
          updateConversation(activeConversation.id, (conversation) => ({
            messages: [...conversation.messages.filter((message) => message.id !== assistantMessageId), { id: assistantMessageId, role: 'assistant', content: streamedContent }],
          }));
        }
        if (event === 'error') setError(data);
      });
      const refreshedConversation = await getConversation(activeConversation.id);
      setConversations((current) => current.map((conversation) => conversation.id === refreshedConversation.id ? refreshedConversation : conversation));
    } catch (requestError) {
      setError(normalizeApiError(requestError));
    } finally {
      setIsTyping(false);
    }
  };

  const handleSaveEdit = (conversationIdToEdit, messageId, updatedContent) => {
    updateConversation(conversationIdToEdit, (conversation) => ({
      messages: conversation.messages.map((message) =>
        message.id === messageId ? { ...message, content: updatedContent } : message,
      ),
    }));

    setIsTyping(false);
  };

  const handleCopy = async (content) => {
    try {
      await navigator.clipboard.writeText(content);
    } catch (error) {
      console.error('Unable to copy text', error);
    }
  };

  const handleOpenSettings = () => navigate('/settings');
  const handleOpenProfile = () => navigate('/profile');
  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  const showWelcome = !activeConversation || activeConversation.messages.length === 0;

  return (
    <AppLayout
      conversations={conversations}
      activeConversationId={activeConversationId}
      onSelectConversation={handleSelectConversation}
      onNewChat={handleNewChat}
      onRenameConversation={handleRenameConversation}
      onDeleteConversation={handleDeleteConversation}
      onOpenSettings={handleOpenSettings}
      onOpenProfile={handleOpenProfile}
      onLogout={handleLogout}
    >
      <div className="flex h-full flex-col overflow-hidden bg-slate-100">
        <TopBar onOpenSettings={handleOpenSettings} onProfile={handleOpenProfile} />

        {error && <div className="mx-auto mt-3 w-full max-w-5xl rounded-xl bg-red-50 px-3 py-2 text-sm text-red-700">{error}</div>}

        {showWelcome ? (
          <div className="flex flex-1 items-center justify-center overflow-y-auto px-4 py-6">
            <WelcomeScreen suggestions={promptSuggestions} onPromptSelect={(prompt) => appendUserMessage(prompt)} />
          </div>
        ) : (
          <>
            <div className="flex-1 overflow-hidden">
              <MessageList
                messages={activeConversation.messages}
                isTyping={isTyping}
                onCopy={handleCopy}
                onRegenerate={() => {}}
                onSaveEdit={(messageId, updatedContent) => handleSaveEdit(activeConversation.id, messageId, updatedContent)}
                onCancelEdit={() => {}}
              />
            </div>
            <MessageComposer onSend={appendUserMessage} disabled={isTyping} />
          </>
        )}

        {showWelcome && (
          <MessageComposer onSend={appendUserMessage} disabled={isTyping} />
        )}
      </div>
    </AppLayout>
  );
}
