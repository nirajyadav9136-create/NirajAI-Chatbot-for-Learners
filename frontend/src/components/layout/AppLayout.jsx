import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import MobileHeader from './MobileHeader';

export default function AppLayout({
  conversations,
  activeConversationId,
  onSelectConversation,
  onNewChat,
  onRenameConversation,
  onDeleteConversation,
  onOpenSettings,
  onOpenProfile,
  onLogout,
  children,
}) {
  const [mobileSidebarOpen, setMobileSidebarOpen] = useState(false);

  useEffect(() => {
    if (!mobileSidebarOpen) return;
    const onKeyDown = (event) => {
      if (event.key === 'Escape') setMobileSidebarOpen(false);
    };
    window.addEventListener('keydown', onKeyDown);
    return () => window.removeEventListener('keydown', onKeyDown);
  }, [mobileSidebarOpen]);

  return (
    <div className="flex h-screen bg-slate-100 text-slate-800">
      {mobileSidebarOpen && (
        <button
          type="button"
          aria-label="Close sidebar"
          className="fixed inset-0 z-30 bg-slate-950/30 md:hidden"
          onClick={() => setMobileSidebarOpen(false)}
        />
      )}

      <div className="md:hidden">
        <MobileHeader onOpenSidebar={() => setMobileSidebarOpen(true)} onNewChat={onNewChat} />
      </div>

      <div className={`${mobileSidebarOpen ? 'translate-x-0' : '-translate-x-full md:translate-x-0'} fixed inset-y-0 left-0 z-40 transition-transform duration-300 md:static md:z-auto md:translate-x-0`}>
        <Sidebar
          conversations={conversations}
          activeConversationId={activeConversationId}
          onSelectConversation={onSelectConversation}
          onNewChat={onNewChat}
          onRenameConversation={onRenameConversation}
          onDeleteConversation={onDeleteConversation}
          onOpenSettings={onOpenSettings}
          onOpenProfile={onOpenProfile}
          onLogout={onLogout}
          isMobile
          onClose={() => setMobileSidebarOpen(false)}
        />
      </div>

      <div className="hidden md:block">
        <Sidebar
          conversations={conversations}
          activeConversationId={activeConversationId}
          onSelectConversation={onSelectConversation}
          onNewChat={onNewChat}
          onRenameConversation={onRenameConversation}
          onDeleteConversation={onDeleteConversation}
          onOpenSettings={onOpenSettings}
          onOpenProfile={onOpenProfile}
          onLogout={onLogout}
        />
      </div>

      <main className="flex min-w-0 flex-1 flex-col overflow-hidden bg-slate-100">
        <Outlet />
        {children}
      </main>
    </div>
  );
}
