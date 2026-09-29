import React, { useState, useRef, useEffect } from 'react';
import { Bot, Trash2, Sparkles } from 'lucide-react';
import { ChatMessage } from './ChatMessage';
import { ChatInput } from './ChatInput';
import { SuggestedQuestions } from './SuggestedQuestions';
import { LoadingSpinner } from '../common/LoadingSpinner';
import { useChatHistory } from '../../hooks/useChatHistory';
import { chatApi } from '../../api/chatApi';

export function ChatPanel({ repository }) {
  const workspaceId = repository ? repository.workspaceId : null;
  const isReady = repository && repository.indexingStatus === 'READY';

  const { messages, addMessagePair, clearHistory } = useChatHistory(workspaceId);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages, loading]);

  const handleSendMessage = async (question) => {
    if (!workspaceId || !isReady) return;

    setLoading(true);
    setError(null);

    try {
      const response = await chatApi.sendQuestion(question, workspaceId);
      addMessagePair(question, response);
    } catch (err) {
      console.error('Chat error:', err);
      setError(err.message || 'Failed to get answer from AI service. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        height: '100%',
        width: '100%',
        overflow: 'hidden',
        backgroundColor: 'var(--bg-app)',
      }}
    >
      {/* Header with repository chat title and clear history button */}
      <div
        style={{
          padding: '12px 20px',
          borderBottom: '1px solid var(--border-subtle)',
          backgroundColor: 'var(--bg-surface-primary)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Bot size={18} style={{ color: 'var(--accent-primary)' }} />
          <span style={{ fontSize: '13.5px', fontWeight: 700, color: 'var(--text-primary)' }}>
            Codebase Q&A Assistant
          </span>
          <span
            style={{
              fontSize: '11px',
              fontFamily: 'var(--font-mono)',
              color: 'var(--text-muted)',
              backgroundColor: 'var(--bg-surface-tertiary)',
              padding: '2px 6px',
              borderRadius: 'var(--radius-sm)',
            }}
          >
            {repository ? repository.repositoryName : 'Select Repository'}
          </span>
        </div>

        {messages.length > 0 && (
          <button
            onClick={clearHistory}
            style={{
              background: 'none',
              border: 'none',
              color: 'var(--text-muted)',
              fontSize: '12px',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              gap: '4px',
              padding: '4px 8px',
              borderRadius: 'var(--radius-sm)',
              transition: 'color 0.15s ease',
            }}
            title="Clear local conversation history for this repository"
          >
            <Trash2 size={14} />
            <span>Clear History</span>
          </button>
        )}
      </div>

      {/* Messages Scroll Area */}
      <div
        style={{
          flex: 1,
          overflowY: 'auto',
          padding: '20px',
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
        }}
      >
        {messages.length === 0 ? (
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              justifyContent: 'center',
              height: '100%',
              textAlign: 'center',
              maxWidth: '640px',
              margin: '0 auto',
            }}
          >
            <div
              style={{
                width: '48px',
                height: '48px',
                borderRadius: 'var(--radius-lg)',
                backgroundColor: 'rgba(59, 130, 246, 0.12)',
                border: '1px solid rgba(59, 130, 246, 0.3)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                marginBottom: '16px',
                color: 'var(--accent-primary)',
              }}
            >
              <Sparkles size={24} />
            </div>

            <h3 style={{ fontSize: '18px', fontWeight: 700, marginBottom: '8px' }}>
              Ask ANCHOR about {repository ? repository.repositoryName : 'this repository'}
            </h3>
            <p style={{ fontSize: '13.5px', color: 'var(--text-secondary)', lineHeight: 1.5, marginBottom: '20px' }}>
              Ask any question about architecture, code flow, classes, or dependencies in this repository.
            </p>


            {isReady && <SuggestedQuestions onSelectQuestion={handleSendMessage} />}
          </div>
        ) : (
          messages.map((msg) => <ChatMessage key={msg.id} message={msg} />)
        )}

        {/* Loading Indicator when AI is answering */}
        {loading && (
          <div
            style={{
              display: 'flex',
              alignItems: 'center',
              gap: '12px',
              padding: '14px 18px',
              borderRadius: 'var(--radius-lg)',
              backgroundColor: 'var(--bg-surface-primary)',
              border: '1px solid var(--border-subtle)',
              color: 'var(--accent-primary)',
              fontSize: '13px',
              width: 'fit-content',
            }}
          >
            <LoadingSpinner size={16} />
            <span>Retrieving codebase context & generating answer...</span>
          </div>
        )}

        <div ref={messagesEndRef} />
      </div>

      {/* Bottom Chat Input */}
      <div style={{ padding: '16px 20px', borderTop: '1px solid var(--border-subtle)', backgroundColor: 'var(--bg-surface-primary)' }}>
        <ChatInput
          onSendMessage={handleSendMessage}
          disabled={!isReady}
          loading={loading}
          error={error}
        />
      </div>
    </div>
  );
}
