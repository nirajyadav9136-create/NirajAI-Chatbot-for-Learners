import ReactMarkdown from 'react-markdown';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { oneDark } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { Check, Copy } from 'lucide-react';
import { useState } from 'react';
import MessageActions from './MessageActions';

export default function AssistantMessage({ message, onCopy, onRegenerate, onLike, onDislike }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    await onCopy?.(message.content);
    setCopied(true);
    setTimeout(() => setCopied(false), 1400);
  };

  return (
    <div className="group mb-6 flex justify-start">
      <div className="max-w-3xl rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm text-slate-700 shadow-sm">
        <div className="prose prose-slate max-w-none prose-headings:mt-4 prose-headings:mb-2 prose-p:my-2 prose-ul:my-2 prose-ol:my-2 prose-li:my-1 prose-code:rounded prose-code:bg-slate-100 prose-code:px-1.5 prose-code:py-0.5 prose-pre:mt-3 prose-pre:overflow-x-auto prose-pre:rounded-xl prose-pre:bg-slate-950 prose-pre:p-0 prose-pre:text-slate-100 prose-a:text-sky-700 prose-blockquote:border-l-2 prose-blockquote:border-slate-300 prose-blockquote:pl-3 prose-blockquote:text-slate-500">
          <ReactMarkdown
            components={{
              code({ inline, className, children, ...props }) {
                const match = /language-(\w+)/.exec(className || '');
                const language = match ? match[1] : '';
                if (!inline) {
                  return (
                    <div className="overflow-hidden rounded-xl border border-slate-700 bg-slate-950">
                      <div className="flex items-center justify-between border-b border-slate-700 bg-slate-900 px-3 py-2 text-[10px] uppercase tracking-[0.12em] text-slate-300">
                        <span>{language || 'code'}</span>
                        <button
                          type="button"
                          onClick={async () => {
                            await navigator.clipboard.writeText(String(children));
                          }}
                          className="inline-flex items-center gap-1 text-slate-200 hover:text-white"
                        >
                          <Copy size={12} />
                          Copy
                        </button>
                      </div>
                      <SyntaxHighlighter
                        {...props}
                        style={oneDark}
                        language={language || 'text'}
                        PreTag="pre"
                        customStyle={{ margin: 0, background: 'transparent', padding: '1rem', fontSize: '0.8rem' }}
                      >
                        {String(children).replace(/\n$/, '')}
                      </SyntaxHighlighter>
                    </div>
                  );
                }

                return <code className={className} {...props}>{children}</code>;
              },
            }}
          >
            {message.content}
          </ReactMarkdown>
        </div>

        <div className="mt-3 flex items-center justify-end gap-2 text-xs text-slate-500">
          {copied ? (
            <span className="inline-flex items-center gap-1"><Check size={12} /> Copied</span>
          ) : (
            <button type="button" onClick={handleCopy} className="inline-flex items-center gap-1 hover:text-slate-700">
              <Copy size={12} /> Copy
            </button>
          )}
        </div>
      </div>

      <div className="ml-3">
        <MessageActions
          role="assistant"
          onCopy={handleCopy}
          onRegenerate={onRegenerate}
          onLike={onLike}
          onDislike={onDislike}
        />
      </div>
    </div>
  );
}
