import React, { createContext, useContext, useState, useEffect, useCallback, useRef } from 'react';
import { repositoryApi } from '../api/repositoryApi';
import { useAuth } from './AuthContext';

const RepositoryContext = createContext(null);

export function RepositoryProvider({ children }) {
  const { user, isAuthenticated } = useAuth();
  const userId = user ? user.userId : null;

  const [repositories, setRepositories] = useState(() => {
    if (!userId) return [];
    try {
      const cached = localStorage.getItem(`anchor_repos_${userId}`);
      return cached ? JSON.parse(cached) : [];
    } catch (e) {
      return [];
    }
  });

  const [selectedRepoId, setSelectedRepoId] = useState(() => {
    if (!userId) return null;
    return localStorage.getItem(`anchor_selected_repo_${userId}`) || null;
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Track active polling intervals
  const pollTimersRef = useRef({});

  // Sync state to localStorage per user
  useEffect(() => {
    if (userId) {
      try {
        localStorage.setItem(`anchor_repos_${userId}`, JSON.stringify(repositories));
        if (selectedRepoId) {
          localStorage.setItem(`anchor_selected_repo_${userId}`, selectedRepoId);
        } else {
          localStorage.removeItem(`anchor_selected_repo_${userId}`);
        }
      } catch (e) {
        console.warn('Failed to sync repositories to localStorage', e);
      }
    }
  }, [repositories, selectedRepoId, userId]);

  // Fetch repositories from API
  const fetchRepositories = useCallback(async () => {
    if (!isAuthenticated) return;
    setLoading(true);
    setError(null);
    try {
      const data = await repositoryApi.getRepositories();
      setRepositories(data || []);
      // If selectedRepoId is not in list and list has repos, select first
      if (data && data.length > 0) {
        if (!selectedRepoId || !data.some((r) => r.id === selectedRepoId)) {
          setSelectedRepoId(data[0].id);
        }
      } else {
        setSelectedRepoId(null);
      }
    } catch (err) {
      console.error('Failed to fetch repositories:', err);
      setError(err.message || 'Failed to load repositories');
    } finally {
      setLoading(false);
    }
  }, [isAuthenticated, selectedRepoId]);

  useEffect(() => {
    if (isAuthenticated) {
      fetchRepositories();
    } else {
      setRepositories([]);
      setSelectedRepoId(null);
    }
  }, [isAuthenticated, fetchRepositories]);


  // Update a single repo in state
  const updateRepoState = useCallback((repoId, updates) => {
    setRepositories((prev) =>
      prev.map((r) => (r.id === repoId ? { ...r, ...updates } : r))
    );
  }, []);

  // Poll status for a specific repository
  const startPollingStatus = useCallback(
    (repoId) => {
      // Clear existing poll timer for this repo if any
      if (pollTimersRef.current[repoId]) {
        clearInterval(pollTimersRef.current[repoId]);
      }

      const poll = async () => {
        try {
          const statusData = await repositoryApi.getRepositoryStatus(repoId);
          const newStatus = statusData.status;

          updateRepoState(repoId, {
            indexingStatus: newStatus,
            statusMessage: statusData.message,
            updatedAt: statusData.updatedAt,
          });

          // Stop polling if terminal state reached
          if (newStatus === 'READY' || newStatus === 'FAILED') {
            if (pollTimersRef.current[repoId]) {
              clearInterval(pollTimersRef.current[repoId]);
              delete pollTimersRef.current[repoId];
            }
          }
        } catch (err) {
          console.warn(`Polling failed for repo ${repoId}`, err);
        }
      };

      // Poll immediately then set interval
      poll();
      pollTimersRef.current[repoId] = setInterval(poll, 2500);
    },
    [updateRepoState]
  );

  // Monitor repos needing polling
  useEffect(() => {
    repositories.forEach((repo) => {
      const status = repo.indexingStatus;
      if (status === 'IMPORTING' || status === 'INDEXING') {
        if (!pollTimersRef.current[repo.id]) {
          startPollingStatus(repo.id);
        }
      } else {
        if (pollTimersRef.current[repo.id]) {
          clearInterval(pollTimersRef.current[repo.id]);
          delete pollTimersRef.current[repo.id];
        }
      }
    });

    return () => {
      // Cleanup all polling timers on unmount
      Object.values(pollTimersRef.current).forEach(clearInterval);
      pollTimersRef.current = {};
    };
  }, [repositories, startPollingStatus]);

  // Import repository action
  const importRepository = async (url) => {
    setError(null);
    const newRepo = await repositoryApi.importRepository(url);
    
    setRepositories((prev) => [newRepo, ...prev.filter((r) => r.id !== newRepo.id)]);
    setSelectedRepoId(newRepo.id);
    
    // Start polling immediately if not READY
    if (newRepo.indexingStatus !== 'READY' && newRepo.indexingStatus !== 'FAILED') {
      startPollingStatus(newRepo.id);
    }

    return newRepo;
  };

  const selectedRepo = repositories.find((r) => r.id === selectedRepoId) || null;

  const value = {
    repositories,
    selectedRepo,
    selectedRepoId,
    setSelectedRepoId,
    loading,
    error,
    fetchRepositories,
    importRepository,
  };

  return <RepositoryContext.Provider value={value}>{children}</RepositoryContext.Provider>;
}

export function useRepository() {
  const context = useContext(RepositoryContext);
  if (!context) {
    throw new Error('useRepository must be used within a RepositoryProvider');
  }
  return context;
}
