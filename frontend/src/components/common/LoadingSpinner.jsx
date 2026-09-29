import React from 'react';
import { Loader2 } from 'lucide-react';

export function LoadingSpinner({ size = 18, className = '', color = 'var(--accent-primary)' }) {
  return (
    <Loader2
      size={size}
      className={`animate-spin ${className}`}
      style={{ color, display: 'inline-block' }}
    />
  );
}
