export default function Avatar({ name, src, size = 32, className = '' }) {
  const initials = name
    ?.split(' ')
    .map((part) => part[0])
    .slice(0, 2)
    .join('')
    .toUpperCase() || 'N';

  if (src) {
    return <img src={src} alt={name || 'User avatar'} className={`rounded-full object-cover ${className}`} style={{ width: size, height: size }} />;
  }

  return (
    <div
      className={`flex items-center justify-center rounded-full bg-gradient-to-br from-slate-800 to-slate-600 text-xs font-semibold text-white ${className}`}
      style={{ width: size, height: size }}
    >
      {initials}
    </div>
  );
}
