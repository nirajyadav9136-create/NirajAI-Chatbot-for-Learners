import { Bell, Settings, Sparkles } from 'lucide-react';

export default function TopBar({ onOpenSettings, onProfile }) {
  return (
    <header className="sticky top-0 z-10 border-b border-slate-200 bg-white/80 px-5 py-3 backdrop-blur">
      <div className="mx-auto flex max-w-5xl items-center justify-between gap-4">
        <div className="flex items-center gap-3">
          <div className="flex h-8 w-8 items-center justify-center rounded-xl bg-slate-900 text-white">
            <Sparkles size={15} />
          </div>
          <div>
            <div className="text-sm font-medium text-slate-500">Workspace</div>
            <div className="text-base font-semibold text-slate-900">NirajAI Conversation</div>
          </div>
        </div>

        <div className="flex items-center gap-2">
          <button type="button" className="rounded-xl p-2 text-slate-600 transition hover:bg-slate-100 hover:text-slate-900" aria-label="Notifications">
            <Bell size={16} />
          </button>
          <button type="button" onClick={onOpenSettings} className="rounded-xl p-2 text-slate-600 transition hover:bg-slate-100 hover:text-slate-900" aria-label="Settings">
            <Settings size={16} />
          </button>
          <button type="button" onClick={onProfile} className="rounded-xl border border-slate-200 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 hover:bg-slate-50">
            Profile
          </button>
        </div>
      </div>
    </header>
  );
}
