import React, { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { clearAuthTokens, getAccessToken, getCurrentUser, login as loginRequest, logout as logoutRequest, normalizeApiError, register as registerRequest } from '../services/api';

const AUTH_KEY = 'nirajai-auth-user';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    try {
      const raw = localStorage.getItem(AUTH_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  });
  const [isLoading, setIsLoading] = useState(Boolean(getAccessToken()));
  const [error, setError] = useState('');

  useEffect(() => {
    if (!getAccessToken()) return;
    getCurrentUser()
      .then(setUser)
      .catch(() => {
        clearAuthTokens();
        setUser(null);
      })
      .finally(() => setIsLoading(false));
  }, []);

  useEffect(() => {
    if (user) {
      localStorage.setItem(AUTH_KEY, JSON.stringify(user));
      return;
    }

    localStorage.removeItem(AUTH_KEY);
  }, [user]);

  const value = useMemo(
    () => ({
      user,
      isAuthenticated: Boolean(user),
      isLoading,
      error,
      login: async (credentials) => {
        setError('');
        try {
          const response = await loginRequest(credentials);
          const currentUser = await getCurrentUser();
          setUser(currentUser);
          return response;
        } catch (requestError) {
          const message = normalizeApiError(requestError);
          setError(message);
          throw new Error(message);
        }
      },
      register: async (profile) => {
        setError('');
        try {
          const response = await registerRequest(profile);
          const currentUser = await getCurrentUser();
          setUser(currentUser);
          return response;
        } catch (requestError) {
          const message = normalizeApiError(requestError);
          setError(message);
          throw new Error(message);
        }
      },
      logout: async () => {
        await logoutRequest();
        setUser(null);
      },
    }),
    [error, isLoading, user],
  );

  return React.createElement(AuthContext.Provider, { value }, children);
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }

  return context;
};
