export default function LoadingSpinner({ className = '', size = 16 }) {
  return (
    <span
      aria-label="Loading"
      className={`inline-block animate-spin rounded-full border-2 border-slate-200 border-t-slate-700 ${className}`}
      style={{ width: size, height: size }}
    />
  );
}
