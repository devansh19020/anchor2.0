import { useState, useCallback } from 'react';

export function useCopy(timeout = 2000) {
  const [copied, setCopied] = useState(false);

  const copy = useCallback(
    (text) => {
      if (!text) return;
      navigator.clipboard.writeText(text).then(
        () => {
          setCopied(true);
          setTimeout(() => setCopied(false), timeout);
        },
        (err) => {
          console.error('Failed to copy to clipboard', err);
        }
      );
    },
    [timeout]
  );

  return { copied, copy };
}
