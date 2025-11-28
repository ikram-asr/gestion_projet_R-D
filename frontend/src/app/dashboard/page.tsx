'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { projectsService, Projet } from '@/lib/projects';
import { financeService, Budget, Depense } from '@/lib/finance';
import { validationService, TestScientifique } from '@/lib/validation';

export default function DashboardPage() {
  const [projects, setProjects] = useState<Projet[]>([]);
  const [budgets, setBudgets] = useState<Budget[]>([]);
  const [depenses, setDepenses] = useState<Depense[]>([]);
  const [tests, setTests] = useState<TestScientifique[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [projs, buds, deps, tsts] = await Promise.all([
          projectsService.getAll().catch(() => []),
          financeService.getAllBudgets().catch(() => []),
          financeService.getAllDepenses().catch(() => []),
          validationService.getAllTests().catch(() => []),
        ]);
        setProjects(projs);
        setBudgets(buds);
        setDepenses(deps);
        setTests(tsts);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) {
    return <div className="container">Chargement...</div>;
  }

  return (
    <div className="container">
      <h1>Tableau de bord</h1>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px', marginTop: '20px' }}>
        <div className="card">
          <h3>Projets</h3>
          <p style={{ fontSize: '32px', fontWeight: 'bold' }}>{projects.length}</p>
          <Link href="/dashboard/projects">Voir tous les projets</Link>
        </div>
        <div className="card">
          <h3>Budgets</h3>
          <p style={{ fontSize: '32px', fontWeight: 'bold' }}>{budgets.length}</p>
          <Link href="/dashboard/finance">Voir les budgets</Link>
        </div>
        <div className="card">
          <h3>Dépenses</h3>
          <p style={{ fontSize: '32px', fontWeight: 'bold' }}>{depenses.length}</p>
          <Link href="/dashboard/finance">Voir les dépenses</Link>
        </div>
        <div className="card">
          <h3>Tests</h3>
          <p style={{ fontSize: '32px', fontWeight: 'bold' }}>{tests.length}</p>
          <Link href="/dashboard/validation">Voir les tests</Link>
        </div>
      </div>
    </div>
  );
}

