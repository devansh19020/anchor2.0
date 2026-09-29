import React from 'react';
import { FolderGit2, ChevronRight } from 'lucide-react';
import { IndexingStatusBadge } from './IndexingStatusBadge';

export function RepositoryListItem({ repository, isSelected, onSelect }) {
  return (
    <div
      onClick={() => onSelect(repository.id)}
      style={{
        padding: '12px 14px',
        borderRadius: 'var(--radius-md)',
        backgroundColor: isSelected ? 'var(--bg-surface-tertiary)' : 'transparent',
        border: `1px solid ${isSelected ? 'var(--border-strong)' : 'transparent'}`,
        cursor: 'pointer',
        transition: 'all 0.15s ease',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        gap: '10px',
        marginBottom: '4px',
      }}
      className={`repo-list-item ${isSelected ? 'selected' : ''}`}
    >
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px', minWidth: 0 }}>
        <FolderGit2
          size={16}
          style={{
            color: isSelected ? 'var(--accent-primary)' : 'var(--text-muted)',
            flexShrink: 0,
          }}
        />
        <div style={{ display: 'flex', flexDirection: 'column', minWidth: 0 }}>
          <span
            style={{
              fontSize: '13px',
              fontWeight: isSelected ? 700 : 500,
              color: isSelected ? 'var(--text-primary)' : 'var(--text-secondary)',
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
            }}
          >
            {repository.repositoryName}
          </span>
          <span
            style={{
              fontSize: '11px',
              color: 'var(--text-muted)',
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
            }}
          >
            {repository.owner}
          </span>
        </div>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', gap: '6px', flexShrink: 0 }}>
        <IndexingStatusBadge status={repository.indexingStatus} showText={false} size="sm" />
        {isSelected && <ChevronRight size={14} style={{ color: 'var(--accent-primary)' }} />}
      </div>
    </div>
  );
}
