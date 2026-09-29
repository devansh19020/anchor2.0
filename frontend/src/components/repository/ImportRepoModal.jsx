import React, { useState } from 'react';
import { GitBranch, Sparkles, ArrowRight } from 'lucide-react';

import { Modal } from '../common/Modal';
import { Input } from '../common/Input';
import { Button } from '../common/Button';
import { useRepository } from '../../context/RepositoryContext';

const EXAMPLE_REPOS = [
  'https://github.com/spring-projects/spring-petclinic',
  'https://github.com/facebook/react',
  'https://github.com/expressjs/express',
];

export function ImportRepoModal({ isOpen, onClose }) {
  const { importRepository } = useRepository();
  const [url, setUrl] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const validateGithubUrl = (input) => {
    if (!input || !input.trim()) {
      return 'Repository URL is required';
    }
    const trimmed = input.trim();
    const githubRegex = /^https?:\/\/(www\.)?github\.com\/[\w-]+\/[\w.-]+\/?$/i;
    if (!githubRegex.test(trimmed)) {
      return 'Please enter a valid GitHub repository URL (e.g. https://github.com/owner/repository)';
    }
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationError = validateGithubUrl(url);
    if (validationError) {
      setError(validationError);
      return;
    }

    setLoading(true);
    setError('');

    try {
      await importRepository(url.trim());
      setUrl('');
      onClose();
    } catch (err) {
      console.error('Import error:', err);
      setError(err.message || 'Failed to import repository. Please check the URL and try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleSelectExample = (exampleUrl) => {
    setUrl(exampleUrl);
    setError('');
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} title="Import Public GitHub Repository">
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        <div>
          <p style={{ fontSize: '13.5px', color: 'var(--text-secondary)', marginBottom: '14px' }}>
            Provide the HTTPS URL of a public GitHub repository. ANCHOR will analyze the repository structure and index your codebase for AI exploration.
          </p>

          <Input
            label="GitHub Repository URL"
            placeholder="https://github.com/owner/repository"
            value={url}
            onChange={(e) => {
              setUrl(e.target.value);
              if (error) setError('');
            }}
            icon={GitBranch}
            error={error}
            required
            autoFocus
          />
        </div>

        {/* Quick Suggestions */}
        <div>
          <span style={{ fontSize: '11.5px', fontWeight: 600, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
            Or try an example repository:
          </span>
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px', marginTop: '8px' }}>
            {EXAMPLE_REPOS.map((example) => (
              <button
                key={example}
                type="button"
                onClick={() => handleSelectExample(example)}
                style={{
                  fontSize: '11.5px',
                  fontFamily: 'var(--font-mono)',
                  padding: '4px 10px',
                  borderRadius: 'var(--radius-sm)',
                  backgroundColor: 'var(--bg-surface-tertiary)',
                  border: '1px solid var(--border-subtle)',
                  color: 'var(--accent-primary)',
                  cursor: 'pointer',
                  transition: 'all 0.15s ease',
                }}
              >
                {example.replace('https://github.com/', '')}
              </button>
            ))}
          </div>
        </div>

        {/* Asynchronous Pipeline Notice */}
        <div
          style={{
            padding: '12px 16px',
            borderRadius: 'var(--radius-md)',
            backgroundColor: 'rgba(59, 130, 246, 0.08)',
            border: '1px solid rgba(59, 130, 246, 0.2)',
            display: 'flex',
            alignItems: 'flex-start',
            gap: '12px',
          }}
        >
          <Sparkles size={18} style={{ color: 'var(--accent-primary)', flexShrink: 0, marginTop: '2px' }} />
          <div style={{ fontSize: '12px', color: 'var(--text-secondary)', lineHeight: 1.5 }}>
            <strong style={{ color: 'var(--text-primary)' }}>Asynchronous Indexing</strong>
            <p style={{ marginTop: '2px' }}>
              Repository indexing runs in the background. Status will update live in your sidebar. AI chat becomes available once status reaches <span style={{ color: 'var(--status-ready)', fontWeight: 700 }}>READY</span>.
            </p>
          </div>
        </div>


        {/* Action buttons */}
        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px', marginTop: '8px' }}>
          <Button variant="outline" onClick={onClose} disabled={loading}>
            Cancel
          </Button>
          <Button
            type="submit"
            variant="primary"
            loading={loading}
            icon={ArrowRight}
            iconPosition="right"
          >
            Import Repository
          </Button>
        </div>
      </form>
    </Modal>
  );
}
