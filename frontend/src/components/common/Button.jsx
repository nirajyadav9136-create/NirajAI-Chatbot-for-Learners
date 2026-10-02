import clsx from 'clsx';

export default function Button({
  children,
  variant = 'primary',
  className = '',
  fullWidth = false,
  ...props
}) {
  const variants = {
    primary: 'bg-slate-900 text-white hover:bg-slate-800',
    secondary: 'bg-slate-100 text-slate-800 hover:bg-slate-200',
    ghost: 'bg-transparent text-slate-600 hover:bg-slate-100',
    subtle: 'bg-white text-slate-700 border border-slate-200 hover:bg-slate-50',
  };

  return (
    <button
      type="button"
      className={clsx(
        'inline-flex items-center justify-center rounded-xl px-4 py-2.5 text-sm font-medium transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-slate-300 disabled:cursor-not-allowed disabled:opacity-60',
        variants[variant],
        fullWidth && 'w-full',
        className,
      )}
      {...props}
    >
      {children}
    </button>
  );
}
