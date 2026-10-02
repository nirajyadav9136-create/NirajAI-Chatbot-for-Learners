import { Archive, MoreHorizontal, PencilLine, Search, Settings, Share2, Trash2, LogOut, Plus, Sparkles } from 'lucide-react';
import { NavLink } from 'react-router-dom';
import Dropdown from '../common/Dropdown';
import Avatar from '../common/Avatar';

const conversationGroups = [
  { label: 'Today', items: ['Explain Spring Boot', 'React project architecture', 'Python ML help'] },
  { label: 'Yesterday', items: ['PostgreSQL query', 'Java interview preparation'] },
  { label: 'Previous 7 days', items: ['AI agent architecture', 'RAG implementation'] },
];

export default function Sidebar({ conversations = [], activeConversationId, onSelectConversation, onNewChat, onRenameConversation, onDeleteConversation, onOpenSettings, onOpenProfile, onLogout, isMobile = false, onClose }) {
  return (
    <aside className={`${isMobile ? 'relative z-40 h-full w-72 max-w-[80vw] border-r border-slate-200 bg-white shadow-2xl' : 'hidden h-full w-[260px] border-r border-slate-200 bg-white md:flex'} flex-col`}>
      <div className="border-b border-slate-200 p-4">
        <div className="flex items-center justify-between gap-2">
          <div className="flex items-center gap-3">
            <div className="flex h-9 w-9 items-center justify-center rounded-xl bg-slate-900 text-white shadow-sm">
              <Sparkles size={16} />
            </div>
            <div>
              <div className="text-lg font-semibold text-slate-900">NirajAI</div>
            </div>
          </div>
          {isMobile && (
            <button type="button" onClick={onClose} className="rounded-lg p-2 text-slate-500 hover:bg-slate-100">✕</button>
          )}
        </div>

        <button type="button" onClick={onNewChat} className="mt-4 flex w-full items-center justify-center gap-2 rounded-xl bg-slate-900 px-3 py-2.5 text-sm font-medium text-white transition hover:bg-slate-800">
          <Plus size={16} />
          New Chat
        </button>

        <div className="mt-4 flex items-center gap-2 rounded-xl border border-slate-200 bg-slate-50 px-3 py-2 text-sm text-slate-500">
          <Search size={14} />
          <input className="w-full border-0 bg-transparent text-sm text-slate-700 placeholder:text-slate-400 focus:outline-none" placeholder="Search conversations" />
        </div>
      </div>

      <div className="flex-1 overflow-y-auto px-3 py-3">
        {conversations.length === 0 && <p className="px-2 py-4 text-sm text-slate-500">No conversations yet.</p>}
        {conversationGroups.map((group, groupIndex) => (
          <div key={group.label} className="mb-4">
            <div className="mb-2 px-2 text-[11px] font-semibold uppercase tracking-[0.12em] text-slate-400">{group.label}</div>
            <div className="space-y-1">
              {conversations.filter((_, index) => index % conversationGroups.length === groupIndex).map((matchingConversation) => {
                const title = matchingConversation.title;
                const active = matchingConversation?.id === activeConversationId;

                return (
                  <button
                    key={title}
                    type="button"
                    onClick={() => matchingConversation && onSelectConversation(matchingConversation.id)}
                    className={`flex w-full items-center justify-between gap-3 rounded-xl px-2.5 py-2 text-left transition ${active ? 'bg-slate-100 text-slate-900' : 'text-slate-600 hover:bg-slate-50'}`}
                  >
                    <span className="truncate text-sm font-medium">{title}</span>
                    <Dropdown
                      trigger={<button type="button" className="rounded-lg p-1 text-slate-400 hover:bg-slate-200 hover:text-slate-700"><MoreHorizontal size={14} /></button>}
                      items={[
                        { label: 'Rename', icon: <PencilLine size={14} />, onClick: () => onRenameConversation?.(matchingConversation.id, window.prompt('Conversation title', title) || title) },
                        { label: 'Share', icon: <Share2 size={14} /> },
                        { label: 'Archive', icon: <Archive size={14} /> },
                        { label: 'Delete', icon: <Trash2 size={14} />, onClick: () => onDeleteConversation?.(matchingConversation.id) },
                      ]}
                    />
                  </button>
                );
              })}
            </div>
          </div>
        ))}
      </div>

      <div className="border-t border-slate-200 p-3">
        <div className="mb-2 rounded-xl bg-slate-50 px-3 py-2">
          <div className="flex items-center justify-between text-xs uppercase tracking-[0.12em] text-slate-400">
            <span>Plan</span>
            <span className="rounded-full bg-emerald-100 px-2 py-0.5 text-[10px] font-semibold text-emerald-700">Pro</span>
          </div>
          <div className="mt-2 text-sm font-medium text-slate-700">Upgrade for advanced AI models</div>
        </div>

        <button type="button" onClick={onOpenProfile} className="flex w-full items-center gap-3 rounded-xl px-2.5 py-2 text-left text-slate-700 transition hover:bg-slate-100">
          <Avatar name="Niraj User" size={32} />
          <div className="flex-1">
            <div className="text-sm font-medium">Niraj User</div>
            <div className="text-xs text-slate-500">niraj@example.com</div>
          </div>
        </button>

        <div className="mt-2 space-y-1">
          <NavLink to="/learning" className="flex w-full items-center gap-2 rounded-xl px-2.5 py-2 text-sm text-slate-600 transition hover:bg-slate-100">Learning</NavLink>
          <NavLink to="/quizzes" className="flex w-full items-center gap-2 rounded-xl px-2.5 py-2 text-sm text-slate-600 transition hover:bg-slate-100">Quizzes</NavLink>
          <NavLink to="/documents" className="flex w-full items-center gap-2 rounded-xl px-2.5 py-2 text-sm text-slate-600 transition hover:bg-slate-100">Documents</NavLink>
          <button type="button" onClick={onOpenSettings} className="flex w-full items-center gap-2 rounded-xl px-2.5 py-2 text-sm text-slate-600 transition hover:bg-slate-100">
            <Settings size={14} />
            Settings
          </button>
          <button type="button" onClick={onLogout} className="flex w-full items-center gap-2 rounded-xl px-2.5 py-2 text-sm text-slate-600 transition hover:bg-slate-100">
            <LogOut size={14} />
            Logout
          </button>
        </div>
      </div>
    </aside>
  );
}
