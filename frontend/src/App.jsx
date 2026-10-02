import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import ChatPage from './pages/Chat';
import LoginPage from './pages/Login';
import RegisterPage from './pages/Register';
import SettingsPage from './pages/Settings';
import ProfilePage from './pages/Profile';
import LearningPage from './pages/Learning';
import QuizzesPage from './pages/Quizzes';
import DocumentsPage from './pages/Documents';
import { AuthProvider, useAuth } from './hooks/useAuth';

function ProtectedRoute({ children }) {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

function AppRoutes() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return <div className="flex min-h-screen items-center justify-center bg-slate-100 text-sm text-slate-500">Restoring your session...</div>;
  }

  return (
    <Routes>
      <Route path="/login" element={isAuthenticated ? <Navigate to="/chat" replace /> : <LoginPage />} />
      <Route path="/register" element={isAuthenticated ? <Navigate to="/chat" replace /> : <RegisterPage />} />
      <Route path="/settings" element={<ProtectedRoute><SettingsPage /></ProtectedRoute>} />
      <Route path="/profile" element={<ProtectedRoute><ProfilePage /></ProtectedRoute>} />
      <Route path="/learning" element={<ProtectedRoute><LearningPage /></ProtectedRoute>} />
      <Route path="/quizzes" element={<ProtectedRoute><QuizzesPage /></ProtectedRoute>} />
      <Route path="/documents" element={<ProtectedRoute><DocumentsPage /></ProtectedRoute>} />
      <Route path="/chat/:conversationId?" element={<ProtectedRoute><ChatPage /></ProtectedRoute>} />
      <Route path="*" element={<Navigate to={isAuthenticated ? '/chat' : '/login'} replace />} />
    </Routes>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  );
}
