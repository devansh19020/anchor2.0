import React, { useState } from 'react';
import { GitBranch, Sparkles, ArrowRight, Database, Cpu, FileCode } from 'lucide-react';
import { AppShell } from '../components/layout/AppShell';
import { RepositoryOverview } from '../components/repository/RepositoryOverview';
import { IndexingStatePanel } from '../components/repository/IndexingStatePanel';
import { ChatPanel } from '../components/chat/ChatPanel';
import { Button } from '../components/common/Button';
import { Input } from '../components/common/Input';
import { useRepository } from '../context/RepositoryContext';


const EXAMPLE_SUGGESTIONS = [
  'https://github.com/spring-projects/spring-petclinic',
  'https://github.com/facebook/react',
];

export function WorkspacePage() {
  const { repositories, selectedRepo, importRepository } = useRepository();
  const [quickUrl, setQuickUrl] = useState('');
  const [importing, setImporting] = useState(false);
  const [importError, setImportError] = useState('');


  const handleQuickImport = async (e) => {
    e.preventDefault();
    if (!quickUrl.trim()) return;

    setImporting(true);
    setImportError('');
    try {
      await importRepository(quickUrl.trim());
      setQuickUrl('');
    } catch (err) {
      console.error('Quick import error:', err);
      setImportError(err.message || 'Failed to import repository. Please check the URL.');
    } finally {
      setImporting(false);
    }
  };

  // Case A: User has no repositories at all (Empty State Onboarding)
  if (repositories.length === 0) {
    return (
      <AppShell>
        <div
          style={{
            flex: 1,
            overflowY: 'auto',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            padding: '32px 24px',
            position: 'relative',
          }}
        >
          {/* Subtle Network Graphic Background */}
          <div
            style={{
              position: 'absolute',
              inset: 0,
              backgroundImage:
                'radial-gradient(circle at 50% 40%, rgba(59, 130, 246, 0.07) 0%, transparent 60%)',
              pointerEvents: 'none',
            }}
          />

          <div
            className="animate-fade-in"
            style={{
              width: '100%',
              maxWidth: '680px',
              textAlign: 'center',
              position: 'relative',
              zIndex: 1,
            }}
          >
            {/* Header / Vision */}
            <div style={{ marginBottom: '32px' }}>
              <div
                style={{
                  display: 'inline-flex',
                  alignItems: 'center',
                  gap: '8px',
                  padding: '6px 14px',
                  borderRadius: 'var(--radius-full)',
                  backgroundColor: 'rgba(59, 130, 246, 0.12)',
                  border: '1px solid rgba(59, 130, 246, 0.3)',
                  color: 'var(--accent-primary)',
                  fontSize: '12.5px',
                  fontWeight: 600,
                  marginBottom: '16px',
                }}
              >
                <Sparkles size={14} />
                <span>AI Codebase Intelligence Platform</span>
              </div>

              <h1
                style={{
                  fontSize: '28px',
                  fontWeight: 800,
                  color: 'var(--text-primary)',
                  letterSpacing: '-0.02em',
                  lineHeight: 1.3,
                  marginBottom: '12px',
                }}
              >
                Understand unfamiliar GitHub repositories instantly using AI.
              </h1>

              <p
                style={{
                  fontSize: '14.5px',
                  color: 'var(--text-secondary)',
                  lineHeight: 1.6,
                  maxWidth: '560px',
                  margin: '0 auto',
                }}
              >
                Import a public GitHub repository to perform semantic code search, trace request flows, discover key classes, and ask deep architectural questions.
              </p>
            </div>

            {/* Quick Import Box */}
            <div
              style={{
                backgroundColor: 'var(--bg-surface-primary)',
                border: '1px solid var(--border-strong)',
                borderRadius: 'var(--radius-xl)',
                padding: '28px',
                boxShadow: '0 20px 30px rgba(0, 0, 0, 0.5), 0 0 20px rgba(59, 130, 246, 0.1)',
                marginBottom: '36px',
                textAlign: 'left',
              }}
            >
              <form onSubmit={handleQuickImport} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                <Input
                  label="Import a Public GitHub Repository"
                  placeholder="https://github.com/spring-projects/spring-petclinic"
                  value={quickUrl}
                  onChange={(e) => {
                    setQuickUrl(e.target.value);
                    if (importError) setImportError('');
                  }}
                  icon={GitBranch}
                  error={importError}
                />

                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap', gap: '12px' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <span style={{ fontSize: '11.5px', color: 'var(--text-muted)' }}>Examples:</span>
                    {EXAMPLE_SUGGESTIONS.map((ex) => (
                      <button
                        key={ex}
                        type="button"
                        onClick={() => setQuickUrl(ex)}
                        style={{
                          fontSize: '11.5px',
                          fontFamily: 'var(--font-mono)',
                          color: 'var(--accent-cyan)',
                          background: 'none',
                          border: 'none',
                          cursor: 'pointer',
                          textDecoration: 'underline',
                          padding: 0,
                        }}
                      >
                        {ex.replace('https://github.com/', '')}
                      </button>
                    ))}
                  </div>

                  <Button
                    type="submit"
                    variant="primary"
                    loading={importing}
                    icon={ArrowRight}
                    iconPosition="right"
                    disabled={!quickUrl.trim()}
                  >
                    Import & Index Codebase
                  </Button>
                </div>
              </form>
            </div>

            {/* Compact Indexing Workflow Graphic */}
            <div
              style={{
                backgroundColor: 'var(--bg-surface-secondary)',
                border: '1px solid var(--border-subtle)',
                borderRadius: 'var(--radius-lg)',
                padding: '20px',
                textAlign: 'left',
              }}
            >
              <h3 style={{ fontSize: '12.5px', fontWeight: 700, textTransform: 'uppercase', letterSpacing: '0.05em', color: 'var(--text-muted)', marginBottom: '16px' }}>
                Asynchronous Indexing Architecture
              </h3>

              <div
                style={{
                  display: 'grid',
                  gridTemplateColumns: 'repeat(4, 1fr)',
                  gap: '12px',
                  fontSize: '12px',
                }}
              >
                <div style={{ padding: '10px', borderRadius: 'var(--radius-md)', backgroundColor: 'var(--bg-surface-tertiary)', border: '1px solid var(--border-subtle)' }}>
                  <GitBranch size={16} style={{ color: 'var(--accent-primary)', marginBottom: '4px' }} />
                  <div style={{ fontWeight: 600 }}>1. Import Repo</div>
                  <div style={{ color: 'var(--text-muted)', fontSize: '11px' }}>GitHub URL</div>
                </div>

                <div style={{ padding: '10px', borderRadius: 'var(--radius-md)', backgroundColor: 'var(--bg-surface-tertiary)', border: '1px solid var(--border-subtle)' }}>
                  <FileCode size={16} style={{ color: 'var(--accent-cyan)', marginBottom: '4px' }} />
                  <div style={{ fontWeight: 600 }}>2. Code Parsing</div>
                  <div style={{ color: 'var(--text-muted)', fontSize: '11px' }}>Code Analysis</div>
                </div>

                <div style={{ padding: '10px', borderRadius: 'var(--radius-md)', backgroundColor: 'var(--bg-surface-tertiary)', border: '1px solid var(--border-subtle)' }}>
                  <Cpu size={16} style={{ color: 'var(--accent-violet)', marginBottom: '4px' }} />
                  <div style={{ fontWeight: 600 }}>3. AI Indexing</div>
                  <div style={{ color: 'var(--text-muted)', fontSize: '11px' }}>Semantic Index</div>
                </div>

                <div style={{ padding: '10px', borderRadius: 'var(--radius-md)', backgroundColor: 'var(--bg-surface-tertiary)', border: '1px solid var(--border-subtle)' }}>
                  <Database size={16} style={{ color: 'var(--status-ready)', marginBottom: '4px' }} />
                  <div style={{ fontWeight: 600 }}>4. Code Q&A</div>
                  <div style={{ color: 'var(--text-muted)', fontSize: '11px' }}>AI Assistant</div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </AppShell>
    );
  }

  // Case B: User has imported repositories, render main workspace
  return (
    <AppShell>
      {selectedRepo ? (
        <div style={{ display: 'flex', flexDirection: 'column', height: '100%', overflow: 'hidden' }}>
          <RepositoryOverview repository={selectedRepo} />
          <div style={{ flex: 1, overflow: 'hidden' }}>
            {selectedRepo.indexingStatus === 'READY' ? (
              <ChatPanel repository={selectedRepo} />
            ) : (
              <IndexingStatePanel repository={selectedRepo} />
            )}
          </div>
        </div>
      ) : (
        <div
          style={{
            flex: 1,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            color: 'var(--text-muted)',
            fontSize: '14px',
          }}
        >
          Select a repository from the sidebar to view details and ask questions.
        </div>
      )}
    </AppShell>
  );
}
