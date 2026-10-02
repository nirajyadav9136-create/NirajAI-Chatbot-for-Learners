import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import { getQuiz, listQuizzes, normalizeApiError, submitQuiz } from '../services/api';

const parseOptions = (value) => { try { const parsed = JSON.parse(value); return Array.isArray(parsed) ? parsed : value.split(/\r?\n|,/).map((item) => item.trim()).filter(Boolean); } catch { return value.split(/\r?\n|,/).map((item) => item.trim()).filter(Boolean); } };

export default function QuizzesPage() {
  const [quizzes, setQuizzes] = useState([]);
  const [selected, setSelected] = useState(null);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => { listQuizzes().then(setQuizzes).catch((requestError) => setError(normalizeApiError(requestError))); }, []);
  const chooseQuiz = (quiz) => { getQuiz(quiz.id).then(setSelected).catch((requestError) => setError(normalizeApiError(requestError))); setResult(null); setAnswers({}); };
  const submit = async (event) => { event.preventDefault(); try { setResult(await submitQuiz(selected.id, answers)); } catch (requestError) { setError(normalizeApiError(requestError)); } };

  return <div className="min-h-screen bg-slate-100 p-4 md:p-8"><div className="mx-auto max-w-4xl"><Link to="/chat" className="mb-6 inline-flex items-center gap-2 text-sm text-slate-600"><ArrowLeft size={16} /> Back to chat</Link><h1 className="text-2xl font-semibold">Quizzes</h1>{error && <p className="mt-4 rounded-xl bg-red-50 px-3 py-2 text-sm text-red-700">{error}</p>}<div className="mt-6 grid gap-6 md:grid-cols-[240px_1fr]"><div className="space-y-2">{quizzes.map((quiz) => <button key={quiz.id} type="button" onClick={() => chooseQuiz(quiz)} className="w-full rounded-xl border border-slate-200 bg-white p-3 text-left hover:border-slate-400"><div className="font-medium">{quiz.title}</div><div className="text-sm text-slate-500">{quiz.difficulty}</div></button>)}</div>{selected ? <form onSubmit={submit} className="rounded-2xl border border-slate-200 bg-white p-5">{selected.questions.map((question, index) => <fieldset key={question.id} className="mb-6"><legend className="font-medium">{index + 1}. {question.question}</legend><div className="mt-2 space-y-2">{parseOptions(question.options).map((option) => <label key={option} className="flex items-center gap-2 text-sm"><input required type="radio" name={question.id} value={option} checked={answers[question.id] === option} onChange={() => setAnswers({ ...answers, [question.id]: option })} />{option}</label>)}</div></fieldset>)}<button type="submit" className="rounded-xl bg-slate-900 px-4 py-2 text-sm font-medium text-white">Submit quiz</button>{result && <div className="mt-4 rounded-xl bg-emerald-50 p-3 text-sm text-emerald-800">Score: {result.score}% ({result.questionsCorrect}/{result.questionsTotal})</div>}</form> : <div className="rounded-2xl border border-dashed border-slate-300 p-8 text-sm text-slate-500">Choose a quiz to begin.</div>}</div></div></div>;
}