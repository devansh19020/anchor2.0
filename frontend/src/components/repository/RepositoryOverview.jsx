import React from 'react';
import { GitBranch, ExternalLink, Copy, Check, Code2, FolderGit2, Info } from 'lucide-react';
import { IndexingStatusBadge } from './IndexingStatusBadge';
import { useCopy } from '../../hooks/useCopy';

export function RepositoryOverview({ repository }) {
  const { copied, copy } = useCopy();

  if (!repository) return null;


  const formattedDate = repository.createdAt
    ? new Date(repository.createdAt).toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
      })
    : null;

  return (
    <div
      style={{
        padding: '16px 20px',
        backgroundColor: 'var(--bg-surface-primary)',
        borderBottom: '1px solid var(--border-default)',
        display: 'flex',
        flexDirection: 'column',
        gap: '12px',
      }}
    >
      <div style={{ display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between', flexWrap: 'wrap', gap: '12px' }}>
        <div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flexWrap: 'wrap' }}>
            <FolderGit2 size={18} style={{ color: 'var(--accent-primary)' }} />
            <h1 style={{ fontSize: '18px', fontWeight: 800, color: 'var(--text-primary)', letterSpacing: '-0.01em' }}>
              {repository.owner} / {repository.repositoryName}
            </h1>
            <IndexingStatusBadge status={repository.indexingStatus} size="sm" />
          </div>

          {repository.description && (
            <p style={{ fontSize: '13px', color: 'var(--text-secondary)', marginTop: '4px' }}>
              {repository.description}
            </p>
          )}
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <a
            href={repository.repositoryUrl}
            target="_blank"
            rel="noopener noreferrer"
            style={{
              display: 'inline-flex',
              alignItems: 'center',
              gap: '6px',
              padding: '6px 12px',
              fontSize: '12px',
              fontFamily: 'var(--font-mono)',
              borderRadius: 'var(--radius-sm)',
              backgroundColor: 'var(--bg-surface-secondary)',
              border: '1px solid var(--border-default)',
              color: 'var(--text-primary)',
              textDecoration: 'none',
              transition: 'all 0.15s ease',
            }}
          >
            <span>GitHub</span>
            <ExternalLink size={12} />
          </a>

          <button
            onClick={() => copy(repository.repositoryUrl)}
            style={{
              display: 'inline-flex',
              alignItems: 'center',
              gap: '6px',
              padding: '6px 12px',
              fontSize: '12px',
              borderRadius: 'var(--radius-sm)',
              backgroundColor: 'var(--bg-surface-secondary)',
              border: '1px solid var(--border-default)',
              color: copied ? 'var(--status-ready)' : 'var(--text-secondary)',
              cursor: 'pointer',
            }}
            title="Copy Repository URL"
          >
            {copied ? <Check size={12} /> : <Copy size={12} />}
            <span>{copied ? 'Copied' : 'Copy URL'}</span>
          </button>
        </div>
      </div>

      {/* Technical Metadata Pills */}
      <div
        style={{
          display: 'flex',
          alignItems: 'center',
          gap: '16px',
          flexWrap: 'wrap',
          fontSize: '12px',
          color: 'var(--text-secondary)',
          paddingTop: '8px',
          borderTop: '1px solid var(--border-subtle)',
        }}
      >
        {repository.language && (
          <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Code2 size={14} style={{ color: 'var(--accent-cyan)' }} />
            <span>Language: <strong style={{ color: 'var(--text-primary)' }}>{repository.language}</strong></span>
          </div>
        )}

        {repository.defaultBranch && (
          <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
            <GitBranch size={14} style={{ color: 'var(--accent-primary)' }} />
            <span>Branch: <strong style={{ color: 'var(--text-primary)' }}>{repository.defaultBranch}</strong></span>
          </div>
        )}

        {formattedDate && (
          <div>
            Imported: <strong style={{ color: 'var(--text-primary)' }}>{formattedDate}</strong>
          </div>
        )}

        {/* Workspace Identifier Details */}
        <div style={{ marginLeft: 'auto', display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Info size={13} style={{ color: 'var(--text-muted)' }} />
          <span style={{ fontFamily: 'var(--font-mono)', fontSize: '11px', color: 'var(--text-muted)' }}>
            workspaceId: {repository.workspaceId}
          </span>
        </div>
      </div>
    </div>
  );
}
