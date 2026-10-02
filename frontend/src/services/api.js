import axios from 'axios';

const ACCESS_TOKEN_KEY = 'nirajai-access-token';
const REFRESH_TOKEN_KEY = 'nirajai-refresh-token';

export const getAccessToken = () => localStorage.getItem(ACCESS_TOKEN_KEY);
export const getRefreshToken = () => localStorage.getItem(REFRESH_TOKEN_KEY);

export const clearAuthTokens = () => {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(REFRESH_TOKEN_KEY);
};

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
});

let refreshRequest;

api.interceptors.request.use((config) => {
  const token = getAccessToken();
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status !== 401 || originalRequest?._retry || !getRefreshToken()) {
      return Promise.reject(error);
    }

    originalRequest._retry = true;
    refreshRequest ||= axios.post(`${api.defaults.baseURL}/auth/refresh`, { refreshToken: getRefreshToken() });
    try {
      const { data } = await refreshRequest;
      localStorage.setItem(ACCESS_TOKEN_KEY, data.accessToken);
      localStorage.setItem(REFRESH_TOKEN_KEY, data.refreshToken);
      originalRequest.headers.Authorization = `${data.tokenType || 'Bearer'} ${data.accessToken}`;
      return api(originalRequest);
    } catch (refreshError) {
      clearAuthTokens();
      return Promise.reject(refreshError);
    } finally {
      refreshRequest = undefined;
    }
  },
);

const saveAuthTokens = ({ accessToken, refreshToken }) => {
  localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
  localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
};

export const normalizeApiError = (error) =>
  error.response?.data?.message || error.response?.data?.detail || error.message || 'Request failed';

export const login = async (credentials) => {
  const { data } = await api.post('/auth/login', credentials);
  saveAuthTokens(data);
  return data;
};

export const register = async (credentials) => {
  const { data } = await api.post('/auth/register', credentials);
  saveAuthTokens(data);
  return data;
};

export const getCurrentUser = async () => (await api.get('/auth/me')).data;

export const listConversations = async () => (await api.get('/conversations')).data;
export const getConversation = async (conversationId) => (await api.get(`/conversations/${conversationId}`)).data;
export const createConversation = async (payload) => (await api.post('/conversations', payload)).data;
export const renameConversation = async (conversationId, title) =>
  (await api.patch(`/conversations/${conversationId}`, { title })).data;
export const deleteConversation = async (conversationId) => api.delete(`/conversations/${conversationId}`);
export const getProfile = async () => (await api.get('/users/me/profile')).data;
export const updateProfile = async (payload) => (await api.patch('/users/me/profile', payload)).data;
export const changePassword = async (payload) => api.post('/auth/change-password', payload);
export const listTopics = async () => (await api.get('/topics')).data;
export const listProgress = async () => (await api.get('/progress')).data;
export const listRecommendations = async () => (await api.get('/recommendations')).data;
export const listQuizzes = async () => (await api.get('/quizzes')).data;
export const getQuiz = async (quizId) => (await api.get(`/quizzes/${quizId}`)).data;
export const submitQuiz = async (quizId, answers) => (await api.post(`/quizzes/${quizId}/submit`, { answers })).data;
export const listDocuments = async () => (await api.get('/documents')).data;
export const ingestDocument = async (payload) => (await api.post('/documents', payload)).data;
export const searchDocuments = async (query) => (await api.get('/documents/search', { params: { q: query } })).data;

export const streamChatMessage = async (conversationId, content, onEvent, signal) => {
  const response = await fetch(`${api.defaults.baseURL}/chat/stream`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${getAccessToken()}`,
      'Content-Type': 'application/json',
      Accept: 'text/event-stream',
    },
    body: JSON.stringify({ conversationId, content }),
    signal,
  });

  if (!response.ok) {
    throw new Error(`Chat request failed (${response.status})`);
  }
  if (!response.body) throw new Error('Chat stream is unavailable');

  const reader = response.body.getReader();
  const decoder = new TextDecoder();
  let buffer = '';
  let eventName = 'message';

  const dispatch = (rawEvent) => {
    const lines = rawEvent.split(/\r?\n/);
    const data = lines.filter((line) => line.startsWith('data:')).map((line) => line.slice(5).trimStart()).join('\n');
    const namedEvent = lines.find((line) => line.startsWith('event:'));
    if (namedEvent) eventName = namedEvent.slice(6).trim();
    if (data) onEvent({ event: eventName, data });
    eventName = 'message';
  };

  while (true) {
    const { done, value } = await reader.read();
    buffer += decoder.decode(value || new Uint8Array(), { stream: !done });
    const events = buffer.split(/\r?\n\r?\n/);
    buffer = events.pop() || '';
    events.forEach(dispatch);
    if (done) break;
  }
  if (buffer.trim()) dispatch(buffer);
};

export const logout = async () => {
  const refreshToken = getRefreshToken();
  try {
    if (refreshToken) await api.post('/auth/logout', { refreshToken });
  } finally {
    clearAuthTokens();
  }
};

export default api;
