import React from 'react';
import ReactMarkdown from 'react-markdown';
import { User, Bot, Copy, Check } from 'lucide-react';
import { SourceReferences } from './SourceReferences';
import { useCopy } from '../../hooks/useCopy';

export function ChatMessage({ message }) {
  const isUser = message.role === 'user';
  const { copied, copy } = useCopy();

  // Normalize formatting if response contains bullet symbol + markdown
  const preprocessMarkdown = (text) => {
    if (!text) return '';
    // Replace bullet symbols like '•**' or '• **' with clean markdown bullet '- **'
    return text.replace(/^[ \t]*[•·][ \t]*/gm, '- ');
  };

  const formattedTime = message.timestamp
    ? new Date(message.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    : '';

  return (
    <div
      className="animate-fade-in"
      style={{
        display: 'flex',
        gap: '14px',
        padding: '16px 20px',
        borderRadius: 'var(--radius-lg)',
        backgroundColor: isUser ? 'var(--bg-surface-secondary)' : 'var(--bg-surface-primary)',
        border: `1px solid ${isUser ? 'var(--border-default)' : 'var(--border-subtle)'}`,
        width: '100%',
        boxSizing: 'border-box',
      }}
    >
      {/* Avatar Icon */}
      <div
        style={{
          width: '32px',
          height: '32px',
          borderRadius: 'var(--radius-md)',
          backgroundColor: isUser ? 'rgba(59, 130, 246, 0.15)' : 'rgba(6, 182, 212, 0.15)',
          color: isUser ? 'var(--accent-primary)' : 'var(--accent-cyan)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          flexShrink: 0,
          border: `1px solid ${isUser ? 'rgba(59, 130, 246, 0.3)' : 'rgba(6, 182, 212, 0.3)'}`,
        }}
      >
        {isUser ? <User size={18} /> : <Bot size={18} />}
      </div>

      {/* Message Body */}
      <div style={{ flex: 1, minWidth: 0 }}>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '6px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <span style={{ fontSize: '13px', fontWeight: 700, color: 'var(--text-primary)' }}>
              {isUser ? 'You' : 'ANCHOR Intelligence'}
            </span>
            {formattedTime && (
              <span style={{ fontSize: '11px', color: 'var(--text-muted)' }}>{formattedTime}</span>
            )}
          </div>

          {!isUser && (
            <button
              onClick={() => copy(message.content)}
              title="Copy answer"
              style={{
                background: 'none',
                border: 'none',
                color: copied ? 'var(--status-ready)' : 'var(--text-muted)',
                cursor: 'pointer',
                display: 'flex',
                alignItems: 'center',
                gap: '4px',
                fontSize: '11.5px',
                padding: '2px 6px',
                borderRadius: 'var(--radius-sm)',
                transition: 'all 0.15s ease',
              }}
            >
              {copied ? <Check size={14} /> : <Copy size={14} />}
              <span>{copied ? 'Copied' : 'Copy'}</span>
            </button>
          )}
        </div>

        {/* Formatted Markdown Content */}
        <div className="markdown-content">
          <ReactMarkdown>{preprocessMarkdown(message.content)}</ReactMarkdown>
        </div>

        {/* Source References */}
        {!isUser && message.sources && <SourceReferences sources={message.sources} />}
      </div>
    </div>
  );
}
