import { Menu, Plus } from 'lucide-react';

export default function MobileHeader({ onOpenSidebar, onNewChat }) {
  return (
    <header className="sticky top-0 z-20 flex items-center justify-between border-b border-slate-200 bg-white/90 px-4 py-3 backdrop-blur md:hidden">
      <button type="button" onClick={onOpenSidebar} className="rounded-xl p-2 text-slate-600 hover:bg-slate-100">
        <Menu size={20} />
      </button>
      <div className="text-base font-semibold text-slate-900">NirajAI</div>
      <button type="button" onClick={onNewChat} className="rounded-xl bg-slate-900 p-2 text-white">
        <Plus size={18} />
      </button>
    </header>
  );
}
