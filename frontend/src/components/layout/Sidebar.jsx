import { Plus, LogOut, User as UserIcon } from 'lucide-react';

import { AnchorLogo } from '../common/AnchorLogo';
import { Button } from '../common/Button';
import { RepositoryList } from '../repository/RepositoryList';
import { useAuth } from '../../context/AuthContext';
import { useRepository } from '../../context/RepositoryContext';

export function Sidebar({ onOpenImportModal }) {
  const { user, logout } = useAuth();
  const { repositories, selectedRepoId, setSelectedRepoId } = useRepository();

  return (
    <aside
      style={{
        width: '280px',
        height: '100%',
        backgroundColor: 'var(--bg-surface-primary)',
        borderRight: '1px solid var(--border-default)',
        display: 'flex',
        flexDirection: 'column',
        userSelect: 'none',
        flexShrink: 0,
      }}
    >
      {/* Top Header Branding */}
      <div
        style={{
          padding: '18px 20px',
          borderBottom: '1px solid var(--border-subtle)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <AnchorLogo size={24} />
      </div>

      {/* Primary Import Repo Action */}
      <div style={{ padding: '16px 16px 12px 16px' }}>
        <Button
          variant="primary"
          icon={Plus}
          fullWidth
          onClick={onOpenImportModal}
          style={{ justifyContent: 'center' }}
        >
          Import Repository
        </Button>
      </div>

      {/* Repositories Header */}
      <div
        style={{
          padding: '8px 16px 6px 16px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <span
          style={{
            fontSize: '11px',
            fontWeight: 700,
            textTransform: 'uppercase',
            letterSpacing: '0.06em',
            color: 'var(--text-muted)',
          }}
        >
          Repositories ({repositories.length})
        </span>
      </div>

      {/* Repository List */}
      <div style={{ flex: 1, overflow: 'hidden' }}>
        <RepositoryList
          repositories={repositories}
          selectedId={selectedRepoId}
          onSelectRepo={setSelectedRepoId}
        />
      </div>

      {/* User Footer Account Section */}
      <div
        style={{
          padding: '14px 16px',
          borderTop: '1px solid var(--border-subtle)',
          backgroundColor: 'var(--bg-surface-secondary)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', minWidth: 0 }}>
          <div
            style={{
              width: '32px',
              height: '32px',
              borderRadius: 'var(--radius-full)',
              backgroundColor: 'rgba(59, 130, 246, 0.15)',
              border: '1px solid rgba(59, 130, 246, 0.3)',
              color: 'var(--accent-primary)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontWeight: 700,
              fontSize: '13px',
              flexShrink: 0,
            }}
          >
            {user?.name ? user.name.charAt(0).toUpperCase() : <UserIcon size={16} />}
          </div>
          <div style={{ display: 'flex', flexDirection: 'column', minWidth: 0 }}>
            <span
              style={{
                fontSize: '13px',
                fontWeight: 600,
                color: 'var(--text-primary)',
                whiteSpace: 'nowrap',
                overflow: 'hidden',
                textOverflow: 'ellipsis',
              }}
            >
              {user?.name || 'Developer'}
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
              {user?.email || ''}
            </span>
          </div>
        </div>

        <button
          onClick={logout}
          style={{
            background: 'none',
            border: 'none',
            color: 'var(--text-muted)',
            cursor: 'pointer',
            padding: '6px',
            borderRadius: 'var(--radius-sm)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            transition: 'color 0.15s ease',
          }}
          title="Logout"
          aria-label="Logout"
        >
          <LogOut size={16} />
        </button>
      </div>
    </aside>
  );
}
