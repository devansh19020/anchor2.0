import React from 'react';
import { FileCode, Layers } from 'lucide-react';

export function SourceReferences({ sources }) {
  if (!sources || !Array.isArray(sources) || sources.length === 0) {
    return null;
  }

  return (
    <div
      style={{
        marginTop: '14px',
        paddingTop: '12px',
        borderTop: '1px solid var(--border-subtle)',
        display: 'flex',
        flexDirection: 'column',
        gap: '8px',
      }}
    >
      <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
        <Layers size={13} style={{ color: 'var(--accent-cyan)' }} />
        <span
          style={{
            fontSize: '11px',
            fontWeight: 700,
            textTransform: 'uppercase',
            letterSpacing: '0.06em',
            color: 'var(--text-muted)',
          }}
        >
          Grounding Source Code Context ({sources.length})
        </span>
      </div>

      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '6px' }}>
        {sources.map((source, index) => {
          // Format filename cleanly
          const pathParts = typeof source === 'string' ? source.split('/') : [];
          const fileName = pathParts.length > 0 ? pathParts[pathParts.length - 1] : String(source);

          return (
            <div
              key={index}
              title={typeof source === 'string' ? source : JSON.stringify(source)}
              style={{
                display: 'inline-flex',
                alignItems: 'center',
                gap: '6px',
                padding: '4px 10px',
                borderRadius: 'var(--radius-sm)',
                backgroundColor: 'rgba(6, 182, 212, 0.08)',
                border: '1px solid rgba(6, 182, 212, 0.2)',
                color: 'var(--accent-cyan)',
                fontFamily: 'var(--font-mono)',
                fontSize: '11.5px',
                transition: 'all 0.15s ease',
                cursor: 'default',
              }}
            >
              <FileCode size={12} style={{ flexShrink: 0 }} />
              <span style={{ fontWeight: 600 }}>{fileName}</span>
              {pathParts.length > 1 && (
                <span style={{ opacity: 0.5, fontSize: '10.5px' }}>
                  ({pathParts.slice(0, -1).join('/')})
                </span>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
