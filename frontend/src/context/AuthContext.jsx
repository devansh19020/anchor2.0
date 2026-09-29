import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import { authApi } from '../api/authApi';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const cached = sessionStorage.getItem('anchor_user');
    return cached ? JSON.parse(cached) : null;
  });
  const [token, setToken] = useState(() => sessionStorage.getItem('anchor_token'));
  const [loading, setLoading] = useState(true);

  // Restore & verify session
  const verifySession = useCallback(async () => {
    const existingToken = sessionStorage.getItem('anchor_token');
    if (!existingToken) {
      setUser(null);
      setLoading(false);
      return;
    }

    try {
      const userProfile = await authApi.getCurrentUser();
      setUser(userProfile);
      sessionStorage.setItem('anchor_user', JSON.stringify(userProfile));
    } catch (err) {
      console.error('Session verification failed:', err);
      // Session invalid or expired
      sessionStorage.removeItem('anchor_token');
      sessionStorage.removeItem('anchor_user');
      setToken(null);
      setUser(null);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    verifySession();
  }, [verifySession]);

  // Global handler for 401 Unauthorized
  useEffect(() => {
    const handleUnauthorized = () => {
      sessionStorage.removeItem('anchor_token');
      sessionStorage.removeItem('anchor_user');
      setToken(null);
      setUser(null);
    };

    window.addEventListener('anchor_auth_unauthorized', handleUnauthorized);
    return () => window.removeEventListener('anchor_auth_unauthorized', handleUnauthorized);
  }, []);

  const login = async (credentials) => {
    const res = await authApi.login(credentials);
    const userObj = {
      userId: res.userId,
      name: res.name,
      email: res.email,
    };
    sessionStorage.setItem('anchor_token', res.token);
    sessionStorage.setItem('anchor_user', JSON.stringify(userObj));
    setToken(res.token);
    setUser(userObj);
    return res;
  };

  const register = async (details) => {
    const res = await authApi.register(details);
    const userObj = {
      userId: res.userId,
      name: res.name,
      email: res.email,
    };
    sessionStorage.setItem('anchor_token', res.token);
    sessionStorage.setItem('anchor_user', JSON.stringify(userObj));
    setToken(res.token);
    setUser(userObj);
    return res;
  };

  const logout = () => {
    sessionStorage.removeItem('anchor_token');
    sessionStorage.removeItem('anchor_user');
    setToken(null);
    setUser(null);
  };

  const value = {
    user,
    token,
    loading,
    isAuthenticated: !!token && !!user,
    login,
    register,
    logout,
    verifySession,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
