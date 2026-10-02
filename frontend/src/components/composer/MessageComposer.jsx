import { Mic, Plus, SendHorizontal } from 'lucide-react';
import { useState } from 'react';

export default function MessageComposer({ onSend, disabled }) {
  const [draft, setDraft] = useState('');

  const handleSubmit = () => {
    const trimmed = draft.trim();
    if (!trimmed || disabled) return;
    onSend(trimmed);
    setDraft('');
  };

  return (
    <div className="border-t border-slate-200 bg-white/95 px-4 pb-4 pt-3 backdrop-blur md:px-6">
      <div className="mx-auto max-w-5xl rounded-2xl border border-slate-200 bg-slate-50 px-3 py-2 shadow-sm">
        <div className="flex items-end gap-2">
          <button type="button" className="flex h-10 w-10 items-center justify-center rounded-xl text-slate-600 transition hover:bg-slate-200" aria-label="Add attachment">
            <Plus size={18} />
          </button>

          <textarea
            value={draft}
            onChange={(event) => setDraft(event.target.value)}
            rows={1}
            placeholder="Message NirajAI..."
            className="max-h-36 min-h-12 flex-1 resize-none border-0 bg-transparent px-2 py-3 text-sm text-slate-700 outline-none placeholder:text-slate-400"
            onKeyDown={(event) => {
              if (event.key === 'Enter' && !event.shiftKey) {
                event.preventDefault();
                handleSubmit();
              }
            }}
          />

          <button type="button" className="flex h-10 w-10 items-center justify-center rounded-xl text-slate-600 transition hover:bg-slate-200" aria-label="Voice input">
            <Mic size={18} />
          </button>

          <div className="flex items-center gap-2 border-l border-slate-200 pl-2">
            <select className="rounded-xl border border-slate-200 bg-white px-2 py-1.5 text-xs text-slate-600 focus:outline-none" defaultValue="GPT-4.1">
              <option>GPT-4.1</option>
              <option>GPT-4o</option>
              <option>Claude</option>
            </select>
            <button
              type="button"
              onClick={handleSubmit}
              disabled={!draft.trim() || disabled}
              className="flex h-10 w-10 items-center justify-center rounded-xl bg-slate-900 text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:bg-slate-300"
              aria-label="Send message"
            >
              <SendHorizontal size={18} />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
