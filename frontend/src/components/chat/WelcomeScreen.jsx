import { Sparkles } from 'lucide-react';
import PromptSuggestions from './PromptSuggestions';

export default function WelcomeScreen({ suggestions, onPromptSelect }) {
  return (
    <div className="flex h-full flex-col items-center justify-center px-6 py-10 text-center">
      <div className="mb-6 flex h-14 w-14 items-center justify-center rounded-2xl bg-slate-900 text-white shadow-lg shadow-slate-200">
        <Sparkles size={22} />
      </div>

      <h1 className="text-3xl font-semibold tracking-tight text-slate-900 md:text-4xl">
        NirajAI
      </h1>
      <p className="mt-3 text-lg text-slate-600">How can I help you today?</p>

      <PromptSuggestions suggestions={suggestions} onSelect={onPromptSelect} />
    </div>
  );
}
