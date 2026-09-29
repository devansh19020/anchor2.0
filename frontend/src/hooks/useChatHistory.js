import { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../context/AuthContext';

/**
 * Hook to persist and retrieve chat history from localStorage, scoped by user and workspace.
 */
export function useChatHistory(workspaceId) {
  const { user } = useAuth();
  const userId = user ? user.userId : 'anonymous';

  const getStorageKey = useCallback(() => {
    if (!userId || !workspaceId) return null;
    return `anchor_chat_${userId}_${workspaceId}`;
  }, [userId, workspaceId]);

  const [messages, setMessages] = useState(() => {
    const key = getStorageKey();
    if (!key) return [];
    try {
      const stored = localStorage.getItem(key);
      if (stored) {
        const parsed = JSON.parse(stored);
        return Array.isArray(parsed) ? parsed : [];
      }
    } catch (e) {
      console.warn('Failed to parse chat history from localStorage', e);
    }
    return [];
  });

  // Load messages when workspace or user changes
  useEffect(() => {
    const key = getStorageKey();
    if (!key) {
      setMessages([]);
      return;
    }
    try {
      const stored = localStorage.getItem(key);
      if (stored) {
        const parsed = JSON.parse(stored);
        setMessages(Array.isArray(parsed) ? parsed : []);
      } else {
        setMessages([]);
      }
    } catch (e) {
      console.warn('Error reading chat history', e);
      setMessages([]);
    }
  }, [getStorageKey]);

  // Save messages to localStorage whenever they change
  const saveMessages = useCallback(
    (newMessages) => {
      const key = getStorageKey();
      if (!key) return;
      try {
        localStorage.setItem(key, JSON.stringify(newMessages));
      } catch (e) {
        console.warn('Error saving chat history to localStorage', e);
      }
    },
    [getStorageKey]
  );

  const addMessagePair = useCallback(
    (question, answerResponse) => {
      const userMsg = {
        id: `user-${Date.now()}`,
        role: 'user',
        content: question,
        timestamp: new Date().toISOString(),
      };

      const aiMsg = {
        id: `ai-${Date.now() + 1}`,
        role: 'assistant',
        content: answerResponse.answer,
        sources: answerResponse.sources || [],
        timestamp: new Date().toISOString(),
      };

      setMessages((prev) => {
        const updated = [...prev, userMsg, aiMsg];
        saveMessages(updated);
        return updated;
      });
    },
    [saveMessages]
  );

  const clearHistory = useCallback(() => {
    const key = getStorageKey();
    if (key) {
      localStorage.removeItem(key);
    }
    setMessages([]);
  }, [getStorageKey]);

  return {
    messages,
    addMessagePair,
    clearHistory,
    setMessages,
  };
}
