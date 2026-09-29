import React from 'react';
import { Sparkles } from 'lucide-react';

const SUGGESTIONS = [
  'Explain the architecture of this repository.',
  'Where does application startup happen?',
  'Trace the authentication flow.',
  'Explain the database access layer.',
  'How does data move through the main request flow?',
  'Which classes are responsible for the core business logic?',
];

export function SuggestedQuestions({ onSelectQuestion }) {
  return (
    <div style={{ margin: '16px 0', display: 'flex', flexDirection: 'column', gap: '10px' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
        <Sparkles size={14} style={{ color: 'var(--accent-primary)' }} />
        <span
          style={{
            fontSize: '11.5px',
            fontWeight: 700,
            textTransform: 'uppercase',
            letterSpacing: '0.05em',
            color: 'var(--text-muted)',
          }}
        >
          Suggested Questions for Codebase Analysis
        </span>
      </div>

      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
          gap: '10px',
        }}
      >
        {SUGGESTIONS.map((q, idx) => (
          <button
            key={idx}
            type="button"
            onClick={() => onSelectQuestion(q)}
            style={{
              textAlign: 'left',
              padding: '10px 14px',
              fontSize: '12.5px',
              fontWeight: 500,
              color: 'var(--text-secondary)',
              backgroundColor: 'var(--bg-surface-secondary)',
              border: '1px solid var(--border-default)',
              borderRadius: 'var(--radius-md)',
              cursor: 'pointer',
              transition: 'all 0.18s ease',
              lineHeight: '1.4',
            }}
            className="suggestion-btn"
          >
            "{q}"
          </button>
        ))}
      </div>
    </div>
  );
}
