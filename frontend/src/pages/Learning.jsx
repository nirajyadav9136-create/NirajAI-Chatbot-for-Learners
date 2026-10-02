import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import { listRecommendations, listTopics, listProgress, normalizeApiError } from '../services/api';

export default function LearningPage() {
  const [topics, setTopics] = useState([]);
  const [progress, setProgress] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    Promise.all([listTopics(), listProgress(), listRecommendations()]).then(([loadedTopics, loadedProgress, loadedRecommendations]) => {
      setTopics(loadedTopics);
      setProgress(loadedProgress);
      setRecommendations(loadedRecommendations);
    }).catch((requestError) => setError(normalizeApiError(requestError)));
  }, []);

  return <div className="min-h-screen bg-slate-100 p-4 md:p-8"><div className="mx-auto max-w-5xl">
    <Link to="/chat" className="mb-6 inline-flex items-center gap-2 text-sm text-slate-600"><ArrowLeft size={16} /> Back to chat</Link>
    <h1 className="text-2xl font-semibold text-slate-900">Learning workspace</h1>
    {error && <p className="mt-4 rounded-xl bg-red-50 px-3 py-2 text-sm text-red-700">{error}</p>}
    <div className="mt-6 grid gap-6 md:grid-cols-2">
      <section className="rounded-2xl border border-slate-200 bg-white p-5"><h2 className="font-semibold text-slate-900">Topics</h2><div className="mt-4 space-y-2">{topics.length ? topics.map((topic) => <div key={topic.id} className="rounded-xl bg-slate-50 p-3"><div className="font-medium">{topic.name}</div><div className="text-sm text-slate-500">{topic.category} · {topic.difficulty}</div></div>) : <p className="text-sm text-slate-500">No topics available.</p>}</div></section>
      <section className="rounded-2xl border border-slate-200 bg-white p-5"><h2 className="font-semibold text-slate-900">Your progress</h2><div className="mt-4 space-y-2">{progress.length ? progress.map((item) => <div key={item.topicId} className="rounded-xl bg-slate-50 p-3"><div className="flex justify-between"><span className="font-medium">{item.topicName}</span><span>{item.skillScore}%</span></div><div className="mt-1 text-sm text-slate-500">{item.questionsCorrect} correct of {item.questionsAttempted}</div></div>) : <p className="text-sm text-slate-500">Complete a quiz to start tracking progress.</p>}</div></section>
    </div>
    <section className="mt-6 rounded-2xl border border-slate-200 bg-white p-5"><h2 className="font-semibold text-slate-900">Recommendations</h2><div className="mt-4 grid gap-3 md:grid-cols-2">{recommendations.length ? recommendations.map((item) => <div key={item.topicId} className="rounded-xl border border-slate-200 p-3"><div className="font-medium">{item.topicName}</div><p className="mt-1 text-sm text-slate-500">{item.reason}</p></div>) : <p className="text-sm text-slate-500">Recommendations will appear after topics are loaded.</p>}</div></section>
  </div></div>;
}