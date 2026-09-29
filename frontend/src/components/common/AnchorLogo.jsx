import React from 'react';

export function AnchorLogo({ size = 28, showText = true, className = '' }) {
  return (
    <div className={`anchor-logo-container ${className}`} style={{ display: 'inline-flex', alignItems: 'center', gap: '10px' }}>
      <svg
        width={size}
        height={size}
        viewBox="0 0 32 32"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        style={{ flexShrink: 0 }}
      >
        <defs>
          <linearGradient id="anchorGlow" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" stopColor="#68A7FF" />
            <stop offset="50%" stopColor="#3B82F6" />
            <stop offset="100%" stopColor="#06B6D4" />
          </linearGradient>
          <filter id="nodeGlow" x="-20%" y="-20%" width="140%" height="140%">
            <feGaussianBlur stdDeviation="1" result="blur" />
            <feComposite in="SourceGraphic" in2="blur" operator="over" />
          </filter>
        </defs>

        {/* Central Anchor Shaft */}
        <path
          d="M16 8V25M16 25C11.5 25 7 21 7 16.5M16 25C20.5 25 25 21 25 16.5"
          stroke="url(#anchorGlow)"
          strokeWidth="2.5"
          strokeLinecap="round"
        />

        {/* Top Ring */}
        <circle cx="16" cy="6" r="3" stroke="url(#anchorGlow)" strokeWidth="2.2" fill="#0B0D12" />

        {/* Crossbar with Nodes */}
        <path d="M10 13H22" stroke="url(#anchorGlow)" strokeWidth="2.2" strokeLinecap="round" />

        {/* Graph Nodes */}
        <circle cx="10" cy="13" r="2.2" fill="#06B6D4" filter="url(#nodeGlow)" />
        <circle cx="22" cy="13" r="2.2" fill="#06B6D4" filter="url(#nodeGlow)" />
        <circle cx="16" cy="19" r="2" fill="#68A7FF" />
        <circle cx="7" cy="16.5" r="2" fill="#3B82F6" />
        <circle cx="25" cy="16.5" r="2" fill="#3B82F6" />
      </svg>

      {showText && (
        <span
          style={{
            fontFamily: 'var(--font-sans)',
            fontWeight: 800,
            fontSize: `${size * 0.7}px`,
            letterSpacing: '0.08em',
            color: 'var(--text-primary)',
            textTransform: 'uppercase',
            display: 'flex',
            alignItems: 'center',
            gap: '4px',
          }}
        >
          ANCHOR
          <span
            style={{
              fontSize: '10px',
              fontWeight: 700,
              padding: '1px 5px',
              borderRadius: '4px',
              backgroundColor: 'rgba(59, 130, 246, 0.15)',
              color: 'var(--accent-primary)',
              border: '1px solid rgba(59, 130, 246, 0.3)',
              letterSpacing: '0.05em',
            }}
          >
            AI
          </span>
        </span>
      )}
    </div>
  );
}
