import React, { useState, useRef } from 'react';
import { Send, CornerDownLeft, AlertCircle } from 'lucide-react';
import { Button } from '../common/Button';

export function ChatInput({ onSendMessage, disabled = false, loading = false, error = null }) {
  const [question, setQuestion] = useState('');
  const textareaRef = useRef(null);

  const handleSubmit = (e) => {
    if (e) e.preventDefault();
    if (!question.trim() || disabled || loading) return;

    onSendMessage(question.trim());
    setQuestion('');
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSubmit();
    }
  };

  const handleTextareaChange = (e) => {
    setQuestion(e.target.value);
    // Auto-adjust height
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${Math.min(textareaRef.current.scrollHeight, 180)}px`;
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '8px', width: '100%' }}>
      {error && (
        <div
          style={{
            padding: '10px 14px',
            borderRadius: 'var(--radius-md)',
            backgroundColor: 'rgba(239, 68, 68, 0.1)',
            border: '1px solid rgba(239, 68, 68, 0.3)',
            color: '#F87171',
            fontSize: '12.5px',
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
          }}
        >
          <AlertCircle size={16} style={{ flexShrink: 0 }} />
          <span>{error}</span>
        </div>
      )}

      <form
        onSubmit={handleSubmit}
        style={{
          position: 'relative',
          display: 'flex',
          alignItems: 'flex-end',
          backgroundColor: 'var(--bg-surface-secondary)',
          border: '1px solid var(--border-strong)',
          borderRadius: 'var(--radius-lg)',
          padding: '10px 14px',
          boxShadow: '0 4px 12px rgba(0, 0, 0, 0.3)',
          opacity: disabled ? 0.6 : 1,
        }}
      >
        <textarea
          ref={textareaRef}
          value={question}
          onChange={handleTextareaChange}
          onKeyDown={handleKeyDown}
          placeholder={
            disabled
              ? 'AI chat is locked until repository indexing reaches READY status...'
              : 'Ask anything about this repository code, architecture, or classes... (Shift+Enter for newline)'
          }
          disabled={disabled || loading}
          rows={1}
          style={{
            width: '100%',
            backgroundColor: 'transparent',
            border: 'none',
            outline: 'none',
            color: 'var(--text-primary)',
            fontSize: '13.5px',
            fontFamily: 'var(--font-sans)',
            resize: 'none',
            lineHeight: '1.5',
            maxHeight: '180px',
            paddingRight: '60px',
          }}
        />

        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flexShrink: 0 }}>
          <span
            style={{
              fontSize: '11px',
              color: 'var(--text-muted)',
              display: 'none',
              alignItems: 'center',
              gap: '2px',
            }}
            className="desktop-only"
          >
            <CornerDownLeft size={11} /> Enter
          </span>
          
          <Button
            type="submit"
            variant="primary"
            size="sm"
            disabled={disabled || loading || !question.trim()}
            loading={loading}
            icon={Send}
            style={{ minWidth: '36px', height: '36px', padding: 0 }}
          />
        </div>
      </form>
    </div>
  );
}
