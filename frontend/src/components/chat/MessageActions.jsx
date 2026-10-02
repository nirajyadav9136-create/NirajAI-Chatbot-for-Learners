import { Copy, MoreHorizontal, Pencil, ThumbsDown, ThumbsUp, RotateCcw } from 'lucide-react';

export default function MessageActions({ role, onCopy, onEdit, onRegenerate, onLike, onDislike }) {
  const isAssistant = role === 'assistant';

  return (
    <div className="mt-3 flex items-center gap-1 text-slate-500 opacity-0 transition-opacity duration-200 group-hover:opacity-100 group-focus-within:opacity-100">
      {isAssistant ? (
        <>
          <button type="button" aria-label="Copy response" onClick={onCopy} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <Copy size={14} />
          </button>
          <button type="button" aria-label="Regenerate response" onClick={onRegenerate} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <RotateCcw size={14} />
          </button>
          <button type="button" aria-label="Like response" onClick={onLike} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <ThumbsUp size={14} />
          </button>
          <button type="button" aria-label="Dislike response" onClick={onDislike} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <ThumbsDown size={14} />
          </button>
        </>
      ) : (
        <>
          <button type="button" aria-label="Edit message" onClick={onEdit} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <Pencil size={14} />
          </button>
          <button type="button" aria-label="Copy message" onClick={onCopy} className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
            <Copy size={14} />
          </button>
        </>
      )}
      <button type="button" aria-label="More actions" className="rounded-lg p-1.5 hover:bg-slate-100 hover:text-slate-700">
        <MoreHorizontal size={14} />
      </button>
    </div>
  );
}
