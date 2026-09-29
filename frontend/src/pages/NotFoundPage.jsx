import React from 'react';
import { Link } from 'react-router-dom';
import { AnchorLogo } from '../components/common/AnchorLogo';
import { Button } from '../components/common/Button';

export function NotFoundPage() {
  return (
    <div
      style={{
        minHeight: '100vh',
        width: '100vw',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: 'var(--bg-app)',
        color: 'var(--text-primary)',
        padding: '24px',
        textAlign: 'center',
      }}
    >
      <AnchorLogo size={40} className="marginBottom: '24px'" />
      <h1 style={{ fontSize: '48px', fontWeight: 800, marginTop: '16px', color: 'var(--accent-primary)' }}>404</h1>
      <h2 style={{ fontSize: '20px', fontWeight: 700, marginTop: '8px' }}>Page Not Found</h2>
      <p style={{ fontSize: '14px', color: 'var(--text-secondary)', marginTop: '8px', marginBottom: '24px' }}>
        The page you are looking for does not exist or has been moved.
      </p>
      <Link to="/workspace" style={{ textDecoration: 'none' }}>
        <Button variant="primary">Return to Workspace</Button>
      </Link>
    </div>
  );
}
