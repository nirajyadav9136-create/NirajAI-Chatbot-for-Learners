import { ArrowLeft, Mail, UserCircle2 } from 'lucide-react';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Avatar from '../components/common/Avatar';
import { getCurrentUser, getProfile, normalizeApiError, updateProfile } from '../services/api';

export default function ProfilePage() {
  const [user, setUser] = useState(null);
  const [profile, setProfile] = useState({ displayName: '', bio: '', preferredLanguage: '', experienceLevel: 'BEGINNER', learningGoal: 'GENERAL_LEARNING' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    Promise.all([getCurrentUser(), getProfile()]).then(([currentUser, currentProfile]) => {
      setUser(currentUser);
      setProfile(currentProfile);
    }).catch((requestError) => setError(normalizeApiError(requestError)));
  }, []);

  const saveProfile = async (event) => {
    event.preventDefault();
    try {
      setProfile(await updateProfile(profile));
      setMessage('Profile saved.');
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
            <h1 className="text-2xl font-semibold text-slate-900">Profile</h1>
          </div>
        </div>

        <div className="flex items-center gap-4 rounded-2xl border border-slate-200 bg-slate-50 p-4">
          <Avatar name={profile.displayName || user?.username || 'Learner'} size={64} className="text-lg" />
          <div>
            <div className="text-xl font-semibold text-slate-900">{profile.displayName || user?.username || 'Learner'}</div>
            <div className="text-sm text-slate-500">{profile.bio || 'Add a short bio to personalize your learning workspace.'}</div>
          </div>
        </div>

        <div className="mt-6 space-y-4">
          <div className="flex items-center gap-3 rounded-xl border border-slate-200 p-3">
            <UserCircle2 size={18} className="text-slate-500" />
            <div>
              <div className="text-sm text-slate-500">Name</div>
              <div className="font-medium text-slate-800">{profile.displayName || user?.username || 'Learner'}</div>
            </div>
          </div>
          <div className="flex items-center gap-3 rounded-xl border border-slate-200 p-3">
            <Mail size={18} className="text-slate-500" />
            <div>
              <div className="text-sm text-slate-500">Email</div>
              <div className="font-medium text-slate-800">{user?.email || 'Loading...'}</div>
            </div>
          </div>

        {error && <p className="mt-4 rounded-xl bg-red-50 px-3 py-2 text-sm text-red-700">{error}</p>}
        {message && <p className="mt-4 rounded-xl bg-emerald-50 px-3 py-2 text-sm text-emerald-700">{message}</p>}
        <form className="mt-6 space-y-4" onSubmit={saveProfile}>
          <label className="block text-sm font-medium text-slate-700">Display name<input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={profile.displayName || ''} onChange={(event) => setProfile({ ...profile, displayName: event.target.value })} /></label>
          <label className="block text-sm font-medium text-slate-700">Bio<textarea className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={profile.bio || ''} onChange={(event) => setProfile({ ...profile, bio: event.target.value })} /></label>
          <button type="submit" className="rounded-xl bg-slate-900 px-4 py-2 text-sm font-medium text-white">Save profile</button>
        </form>
        </div>
      </div>
    </div>
  );
}
