import React from 'react';
import { CheckCircle2, AlertCircle, Loader2, Clock } from 'lucide-react';

export function IndexingStatusBadge({ status, showText = true, size = 'md' }) {
  const normalizedStatus = (status || 'IMPORTING').toUpperCase();

  const getStatusConfig = () => {
    switch (normalizedStatus) {
      case 'READY':
        return {
          label: 'READY',
          color: 'var(--status-ready)',
          bg: 'var(--status-ready-bg)',
          border: 'rgba(16, 185, 129, 0.3)',
          icon: CheckCircle2,
          spin: false,
        };
      case 'INDEXING':
        return {
          label: 'INDEXING',
          color: 'var(--status-indexing)',
          bg: 'var(--status-indexing-bg)',
          border: 'rgba(245, 158, 11, 0.3)',
          icon: Loader2,
          spin: true,
        };
      case 'IMPORTING':
        return {
          label: 'IMPORTING',
          color: 'var(--status-importing)',
          bg: 'var(--status-importing-bg)',
          border: 'rgba(59, 130, 246, 0.3)',
          icon: Clock,
          spin: false,
        };
      case 'FAILED':
        return {
          label: 'FAILED',
          color: 'var(--status-failed)',
          bg: 'var(--status-failed-bg)',
          border: 'rgba(239, 68, 68, 0.3)',
          icon: AlertCircle,
          spin: false,
        };
      default:
        return {
          label: normalizedStatus,
          color: 'var(--text-secondary)',
          bg: 'var(--bg-surface-tertiary)',
          border: 'var(--border-default)',
          icon: Clock,
          spin: false,
        };
    }
  };

  const config = getStatusConfig();
  const Icon = config.icon;

  const padding = size === 'sm' ? '2px 8px' : '4px 10px';
  const fontSize = size === 'sm' ? '11px' : '12px';
  const iconSize = size === 'sm' ? 12 : 14;

  return (
    <span
      style={{
        display: 'inline-flex',
        alignItems: 'center',
        gap: '6px',
        padding,
        fontSize,
        fontWeight: 600,
        fontFamily: 'var(--font-mono)',
        letterSpacing: '0.04em',
        borderRadius: 'var(--radius-full)',
        color: config.color,
        backgroundColor: config.bg,
        border: `1px solid ${config.border}`,
        whiteSpace: 'nowrap',
      }}
      title={`Repository Indexing Status: ${config.label}`}
    >
      <Icon
        size={iconSize}
        className={config.spin ? 'animate-spin' : ''}
        style={{ color: config.color, flexShrink: 0 }}
      />
      {showText && <span>{config.label}</span>}
    </span>
  );
}
