import { useState } from 'react';
import { Check, Copy } from 'lucide-react';
import MessageActions from './MessageActions';

export default function UserMessage({ message, onEdit, onCopy, onSaveEdit, onCancelEdit }) {
  const [isEditing, setIsEditing] = useState(false);
  const [draft, setDraft] = useState(message.content);
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    await onCopy?.(message.content);
    setCopied(true);
    setTimeout(() => setCopied(false), 1200);
  };

  const submitEdit = () => {
    onSaveEdit?.(message.id, draft);
    setIsEditing(false);
  };

  if (isEditing) {
    return (
      <div className="mb-6 flex justify-end">
        <div className="w-full max-w-2xl rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
          <textarea
            value={draft}
            onChange={(event) => setDraft(event.target.value)}
            className="min-h-28 w-full resize-none border-0 bg-transparent text-sm text-slate-700 outline-none"
            autoFocus
          />
          <div className="mt-3 flex justify-end gap-2">
            <button type="button" onClick={() => { setIsEditing(false); setDraft(message.content); onCancelEdit?.(); }} className="rounded-lg px-3 py-1.5 text-sm text-slate-600 hover:bg-slate-100">Cancel</button>
            <button type="button" onClick={submitEdit} className="rounded-lg bg-slate-900 px-3 py-1.5 text-sm text-white hover:bg-slate-800">Save</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="group mb-6 flex justify-end">
      <div className="max-w-2xl rounded-2xl bg-slate-900 px-4 py-3 text-sm text-slate-50 shadow-sm">
        <div className="whitespace-pre-wrap break-words leading-7">{message.content}</div>
        <div className="mt-3 flex items-center justify-end gap-2 text-xs text-slate-300">
          {copied ? (
            <span className="inline-flex items-center gap-1"><Check size={12} /> Copied</span>
          ) : (
            <button type="button" onClick={handleCopy} className="inline-flex items-center gap-1 hover:text-white">
              <Copy size={12} /> Copy
            </button>
          )}
        </div>
      </div>
      <div className="ml-3">
        <MessageActions
          role="user"
          onEdit={() => {
            setIsEditing(true);
            onEdit?.(message.id);
          }}
          onCopy={handleCopy}
        />
      </div>
    </div>
  );
}
