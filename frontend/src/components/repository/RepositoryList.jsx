import React, { useState } from 'react';
import { Search } from 'lucide-react';
import { RepositoryListItem } from './RepositoryListItem';


export function RepositoryList({ repositories = [], selectedId, onSelectRepo }) {
  const [filter, setFilter] = useState('');

  const filteredRepos = repositories.filter(
    (repo) =>
      repo.repositoryName?.toLowerCase().includes(filter.toLowerCase()) ||
      repo.owner?.toLowerCase().includes(filter.toLowerCase())
  );

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* Search Input */}
      {repositories.length > 3 && (
        <div style={{ padding: '0 12px 10px 12px' }}>
          <div
            style={{
              position: 'relative',
              display: 'flex',
              alignItems: 'center',
            }}
          >
            <Search
              size={13}
              style={{
                position: 'absolute',
                left: '10px',
                color: 'var(--text-muted)',
                pointerEvents: 'none',
              }}
            />
            <input
              type="text"
              placeholder="Filter repositories..."
              value={filter}
              onChange={(e) => setFilter(e.target.value)}
              style={{
                width: '100%',
                padding: '6px 10px 6px 30px',
                fontSize: '12px',
                backgroundColor: 'var(--bg-surface-secondary)',
                border: '1px solid var(--border-subtle)',
                borderRadius: 'var(--radius-sm)',
                color: 'var(--text-primary)',
                outline: 'none',
              }}
            />
          </div>
        </div>
      )}

      {/* List Container */}
      <div style={{ flex: 1, overflowY: 'auto', padding: '0 12px' }}>
        {filteredRepos.length === 0 ? (
          <div
            style={{
              padding: '24px 12px',
              textAlign: 'center',
              color: 'var(--text-muted)',
              fontSize: '12.5px',
            }}
          >
            {filter ? 'No matching repositories' : 'No repositories imported yet'}
          </div>
        ) : (
          filteredRepos.map((repo) => (
            <RepositoryListItem
              key={repo.id}
              repository={repo}
              isSelected={repo.id === selectedId}
              onSelect={onSelectRepo}
            />
          ))
        )}
      </div>
    </div>
  );
}
