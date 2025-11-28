'use client';

import Link from 'next/link';
import { authService } from '@/lib/auth';

export default function Navbar() {
  const handleLogout = () => {
    authService.logout();
  };

  return (
    <nav className="navbar">
      <div className="navbar-content">
        <Link href="/dashboard">
          <h2 style={{ margin: 0 }}>ERP R&D</h2>
        </Link>
        <div className="navbar-links">
          <Link href="/dashboard/projects">Projets</Link>
          <Link href="/dashboard/validation">Validation</Link>
          <Link href="/dashboard/finance">Finance</Link>
          <button onClick={handleLogout} style={{ background: 'none', border: 'none', color: 'white', cursor: 'pointer' }}>
            Déconnexion
          </button>
        </div>
      </div>
    </nav>
  );
}

