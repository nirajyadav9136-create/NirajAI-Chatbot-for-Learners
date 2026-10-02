import { ArrowLeft, Bell, Palette, ShieldCheck } from 'lucide-react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { changePassword, normalizeApiError } from '../services/api';

export default function SettingsPage() {
  const [passwords, setPasswords] = useState({ currentPassword: '', newPassword: '' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const submitPassword = async (event) => {
    event.preventDefault();
    try {
      await changePassword(passwords);
      setPasswords({ currentPassword: '', newPassword: '' });
      setMessage('Password changed.');
    } catch (requestError) {
      setError(normalizeApiError(requestError));
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 p-4 md:p-8">
      <div className="mx-auto max-w-3xl rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
        <div className="mb-6 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <Link to="/chat" className="rounded-xl p-2 text-slate-600 transition hover:bg-slate-100">
              <ArrowLeft size={18} />
            </Link>
            <h1 className="text-2xl font-semibold text-slate-900">Settings</h1>
          </div>
        </div>

        <div className="space-y-4">
          <div className="rounded-2xl border border-slate-200 p-4">
            <div className="mb-2 flex items-center gap-2 text-slate-900">
              <Palette size={16} />
              <span className="font-medium">Appearance</span>
            </div>
            <p className="text-sm text-slate-600">Use system theme with subtle dark mode support planned for later.</p>
          </div>

          <div className="rounded-2xl border border-slate-200 p-4">
            <div className="mb-2 flex items-center gap-2 text-slate-900">
              <Bell size={16} />
              <span className="font-medium">Notifications</span>
            </div>
            <p className="text-sm text-slate-600">Manage message and product updates in a future integration layer.</p>
          </div>

          <div className="rounded-2xl border border-slate-200 p-4">
            <div className="mb-2 flex items-center gap-2 text-slate-900">
              <ShieldCheck size={16} />
              <span className="font-medium">Privacy & security</span>
            </div>
            <p className="text-sm text-slate-600">Update the password used for your backend account.</p>
            <form className="mt-4 space-y-3" onSubmit={submitPassword}>
              <input required type="password" placeholder="Current password" className="w-full rounded-xl border border-slate-200 px-3 py-2 text-sm" value={passwords.currentPassword} onChange={(event) => setPasswords({ ...passwords, currentPassword: event.target.value })} />
              <input required type="password" placeholder="New password" className="w-full rounded-xl border border-slate-200 px-3 py-2 text-sm" value={passwords.newPassword} onChange={(event) => setPasswords({ ...passwords, newPassword: event.target.value })} />
              <button type="submit" className="rounded-xl bg-slate-900 px-3 py-2 text-sm font-medium text-white">Change password</button>
            </form>
          </div>
        </div>
        {error && <p className="mt-4 rounded-xl bg-red-50 px-3 py-2 text-sm text-red-700">{error}</p>}
        {message && <p className="mt-4 rounded-xl bg-emerald-50 px-3 py-2 text-sm text-emerald-700">{message}</p>}
      </div>
    </div>
  );
}
