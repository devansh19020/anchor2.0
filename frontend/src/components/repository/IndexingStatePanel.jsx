import React from 'react';
import { Loader2, AlertTriangle, Cpu, Database, FileCode2 } from 'lucide-react';

import { IndexingStatusBadge } from './IndexingStatusBadge';

export function IndexingStatePanel({ repository }) {
  if (!repository) return null;

  const status = (repository.indexingStatus || 'IMPORTING').toUpperCase();
  const message = repository.statusMessage || '';

  if (status === 'READY') {
    return null; // Ready repositories render the AI Chat panel directly
  }

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100%',
        padding: '32px',
        textAlign: 'center',
        maxWidth: '680px',
        margin: '0 auto',
      }}
    >
      <div
        style={{
          width: '64px',
          height: '64px',
          borderRadius: 'var(--radius-xl)',
          backgroundColor:
            status === 'FAILED' ? 'var(--status-failed-bg)' : 'var(--status-indexing-bg)',
          border: `1px solid ${status === 'FAILED' ? 'rgba(239, 68, 68, 0.3)' : 'rgba(245, 158, 11, 0.3)'}`,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          marginBottom: '20px',
        }}
      >
        {status === 'FAILED' ? (
          <AlertTriangle size={32} style={{ color: 'var(--status-failed)' }} />
        ) : (
          <Loader2 size={32} className="animate-spin" style={{ color: 'var(--status-indexing)' }} />
        )}
      </div>

      <div style={{ marginBottom: '12px' }}>
        <IndexingStatusBadge status={status} size="lg" />
      </div>

      <h2 style={{ fontSize: '20px', fontWeight: 700, marginBottom: '8px' }}>
        {status === 'IMPORTING' && 'Preparing Repository Workspace'}
        {status === 'INDEXING' && 'Indexing Repository & Generating Embeddings'}
        {status === 'FAILED' && 'Indexing Encountered an Error'}
      </h2>

      <p style={{ fontSize: '14px', color: 'var(--text-secondary)', lineHeight: 1.6, marginBottom: '28px' }}>
        {status === 'IMPORTING' &&
          'The repository has been imported and queued for processing. Indexing will begin fetching source files shortly.'}
        {status === 'INDEXING' &&
          (message ||
            'Analyzing source files, parsing project structure, and building semantic code index.')}
        {status === 'FAILED' &&
          (message ||
            'Failed to complete indexing. This could be due to an invalid repository URL, missing permissions, or a temporary network issue.')}
      </p>

      {/* Indexing Pipeline Steps Graphic */}
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(3, 1fr)',
          gap: '16px',
          width: '100%',
          backgroundColor: 'var(--bg-surface-primary)',
          padding: '20px',
          borderRadius: 'var(--radius-lg)',
          border: '1px solid var(--border-default)',
        }}
      >
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '8px' }}>
          <FileCode2 size={20} style={{ color: 'var(--accent-primary)' }} />
          <span style={{ fontSize: '12px', fontWeight: 600 }}>1. Source Parsing</span>
          <span style={{ fontSize: '11px', color: 'var(--text-muted)' }}>Codebase Analysis</span>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '8px' }}>
          <Cpu size={20} style={{ color: 'var(--accent-cyan)' }} />
          <span style={{ fontSize: '12px', fontWeight: 600 }}>2. AI Embeddings</span>
          <span style={{ fontSize: '11px', color: 'var(--text-muted)' }}>Semantic Mapping</span>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '8px' }}>
          <Database size={20} style={{ color: 'var(--accent-violet)' }} />
          <span style={{ fontSize: '12px', fontWeight: 600 }}>3. Ready for Q&A</span>
          <span style={{ fontSize: '11px', color: 'var(--text-muted)' }}>Context-Aware Search</span>
        </div>
      </div>


      <div style={{ marginTop: '24px', fontSize: '12px', color: 'var(--text-muted)' }}>
        Status is automatically polled live every 2.5 seconds.
      </div>
    </div>
  );
}
