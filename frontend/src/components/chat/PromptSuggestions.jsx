import Button from '../common/Button';

export default function PromptSuggestions({ suggestions, onSelect }) {
  return (
    <div className="mt-8 grid w-full max-w-3xl grid-cols-1 gap-3 md:grid-cols-2">
      {suggestions.map((suggestion) => (
        <Button
          key={suggestion}
          variant="secondary"
          className="justify-start rounded-2xl border border-slate-200 bg-white px-4 py-3 text-left text-sm text-slate-700 shadow-sm transition hover:-translate-y-0.5 hover:shadow-lg"
          onClick={() => onSelect(suggestion)}
        >
          {suggestion}
        </Button>
      ))}
    </div>
  );
}
