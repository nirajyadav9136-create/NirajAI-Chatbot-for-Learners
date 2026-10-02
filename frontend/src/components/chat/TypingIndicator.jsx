export default function TypingIndicator() {
  return (
    <div className="flex items-center gap-2 px-2 py-1 text-sm text-slate-500">
      <div className="flex gap-1">
        <span className="h-2 w-2 animate-bounce rounded-full bg-slate-400 [animation-delay:-0.2s]" />
        <span className="h-2 w-2 animate-bounce rounded-full bg-slate-400 [animation-delay:-0.1s]" />
        <span className="h-2 w-2 animate-bounce rounded-full bg-slate-400" />
      </div>
      <span>NirajAI is thinking...</span>
    </div>
  );
}
