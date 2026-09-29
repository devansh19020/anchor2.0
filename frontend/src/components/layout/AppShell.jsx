import React, { useState } from 'react';
import { Sidebar } from './Sidebar';
import { ImportRepoModal } from '../repository/ImportRepoModal';

export function AppShell({ children }) {
  const [isImportModalOpen, setIsImportModalOpen] = useState(false);

  return (
    <div
      style={{
        display: 'flex',
        height: '100vh',
        width: '100vw',
        overflow: 'hidden',
        backgroundColor: 'var(--bg-app)',
      }}
    >
      <Sidebar onOpenImportModal={() => setIsImportModalOpen(true)} />
      
      <main
        style={{
          flex: 1,
          height: '100%',
          overflow: 'hidden',
          display: 'flex',
          flexDirection: 'column',
        }}
      >
        {children}
      </main>

      <ImportRepoModal
        isOpen={isImportModalOpen}
        onClose={() => setIsImportModalOpen(false)}
      />
    </div>
  );
}
