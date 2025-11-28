'use client';

import { useEffect, useState } from 'react';
import { financeService, Budget, Depense, BudgetRequest, DepenseRequest } from '@/lib/finance';
import { toast } from 'react-toastify';
import { useForm } from 'react-hook-form';

export default function FinancePage() {
  const [budgets, setBudgets] = useState<Budget[]>([]);
  const [depenses, setDepenses] = useState<Depense[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState<'budgets' | 'depenses'>('budgets');
  const [showBudgetForm, setShowBudgetForm] = useState(false);
  const [showDepenseForm, setShowDepenseForm] = useState(false);
  const [editingBudget, setEditingBudget] = useState<Budget | null>(null);
  const [editingDepense, setEditingDepense] = useState<Depense | null>(null);
  
  const budgetForm = useForm<BudgetRequest>();
  const depenseForm = useForm<DepenseRequest>();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [buds, deps] = await Promise.all([
        financeService.getAllBudgets(),
        financeService.getAllDepenses(),
      ]);
      setBudgets(buds);
      setDepenses(deps);
    } catch (error: any) {
      toast.error('Erreur lors du chargement des données');
    } finally {
      setLoading(false);
    }
  };

  const onSubmitBudget = async (data: BudgetRequest) => {
    try {
      if (editingBudget) {
        await financeService.updateBudget(editingBudget.idBudget, data);
        toast.success('Budget mis à jour');
      } else {
        await financeService.createBudget(data);
        toast.success('Budget créé');
      }
      budgetForm.reset();
      setShowBudgetForm(false);
      setEditingBudget(null);
      fetchData();
    } catch (error: any) {
      toast.error('Erreur lors de la sauvegarde');
    }
  };

  const onSubmitDepense = async (data: DepenseRequest) => {
    try {
      if (editingDepense) {
        await financeService.updateDepense(editingDepense.idDepense, data);
        toast.success('Dépense mise à jour');
      } else {
        await financeService.createDepense(data);
        toast.success('Dépense créée');
      }
      depenseForm.reset();
      setShowDepenseForm(false);
      setEditingDepense(null);
      fetchData();
    } catch (error: any) {
      toast.error('Erreur lors de la sauvegarde');
    }
  };

  const handleDeleteBudget = async (id: number) => {
    if (!confirm('Supprimer ce budget?')) return;
    try {
      await financeService.deleteBudget(id);
      toast.success('Budget supprimé');
      fetchData();
    } catch (error) {
      toast.error('Erreur lors de la suppression');
    }
  };

  const handleDeleteDepense = async (id: number) => {
    if (!confirm('Supprimer cette dépense?')) return;
    try {
      await financeService.deleteDepense(id);
      toast.success('Dépense supprimée');
      fetchData();
    } catch (error) {
      toast.error('Erreur lors de la suppression');
    }
  };

  if (loading) {
    return <div className="container">Chargement...</div>;
  }

  return (
    <div className="container">
      <h1>Gestion Financière</h1>
      
      <div style={{ marginBottom: '20px' }}>
        <button
          className={`btn ${activeTab === 'budgets' ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setActiveTab('budgets')}
          style={{ marginRight: '10px' }}
        >
          Budgets
        </button>
        <button
          className={`btn ${activeTab === 'depenses' ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setActiveTab('depenses')}
        >
          Dépenses
        </button>
      </div>

      {activeTab === 'budgets' && (
        <>
          <div style={{ marginBottom: '20px' }}>
            <button className="btn btn-primary" onClick={() => { setShowBudgetForm(!showBudgetForm); setEditingBudget(null); budgetForm.reset(); }}>
              {showBudgetForm ? 'Annuler' : 'Nouveau Budget'}
            </button>
          </div>

          {showBudgetForm && (
            <div className="card" style={{ marginBottom: '20px' }}>
              <h2>{editingBudget ? 'Modifier le budget' : 'Nouveau budget'}</h2>
              <form onSubmit={budgetForm.handleSubmit(onSubmitBudget)}>
                <div className="form-group">
                  <label>ID Projet *</label>
                  <input type="number" {...budgetForm.register('idProjet', { required: true, valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>Montant alloué *</label>
                  <input type="number" step="0.01" {...budgetForm.register('montantAlloue', { required: true, valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>Année budgétaire *</label>
                  <input type="number" {...budgetForm.register('anneeBudgetaire', { required: true, valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>Description</label>
                  <textarea {...budgetForm.register('description')} />
                </div>
                <button type="submit" className="btn btn-primary">
                  {editingBudget ? 'Mettre à jour' : 'Créer'}
                </button>
              </form>
            </div>
          )}

          <div className="card">
            <table className="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Projet ID</th>
                  <th>Montant alloué</th>
                  <th>Montant dépensé</th>
                  <th>Année</th>
                  <th>Statut</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {budgets.map((budget) => (
                  <tr key={budget.idBudget}>
                    <td>{budget.idBudget}</td>
                    <td>{budget.idProjet}</td>
                    <td>{budget.montantAlloue.toFixed(2)}</td>
                    <td>{budget.montantDepense.toFixed(2)}</td>
                    <td>{budget.anneeBudgetaire}</td>
                    <td>{budget.statut}</td>
                    <td>
                      <button className="btn btn-danger" onClick={() => handleDeleteBudget(budget.idBudget)}>
                        Supprimer
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}

      {activeTab === 'depenses' && (
        <>
          <div style={{ marginBottom: '20px' }}>
            <button className="btn btn-primary" onClick={() => { setShowDepenseForm(!showDepenseForm); setEditingDepense(null); depenseForm.reset(); }}>
              {showDepenseForm ? 'Annuler' : 'Nouvelle Dépense'}
            </button>
          </div>

          {showDepenseForm && (
            <div className="card" style={{ marginBottom: '20px' }}>
              <h2>{editingDepense ? 'Modifier la dépense' : 'Nouvelle dépense'}</h2>
              <form onSubmit={depenseForm.handleSubmit(onSubmitDepense)}>
                <div className="form-group">
                  <label>ID Projet *</label>
                  <input type="number" {...depenseForm.register('idProjet', { required: true, valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>ID Budget</label>
                  <input type="number" {...depenseForm.register('idBudget', { valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>Montant *</label>
                  <input type="number" step="0.01" {...depenseForm.register('montant', { required: true, valueAsNumber: true })} />
                </div>
                <div className="form-group">
                  <label>Description</label>
                  <textarea {...depenseForm.register('description')} />
                </div>
                <div className="form-group">
                  <label>Catégorie</label>
                  <select {...depenseForm.register('categorie')}>
                    <option value="EQUIPEMENT">Équipement</option>
                    <option value="MATERIEL">Matériel</option>
                    <option value="SALAIRES">Salaires</option>
                    <option value="FORMATION">Formation</option>
                    <option value="DEPLACEMENT">Déplacement</option>
                    <option value="AUTRE">Autre</option>
                  </select>
                </div>
                <div className="form-group">
                  <label>Date</label>
                  <input type="date" {...depenseForm.register('dateDepense')} />
                </div>
                <button type="submit" className="btn btn-primary">
                  {editingDepense ? 'Mettre à jour' : 'Créer'}
                </button>
              </form>
            </div>
          )}

          <div className="card">
            <table className="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Projet ID</th>
                  <th>Montant</th>
                  <th>Catégorie</th>
                  <th>Statut</th>
                  <th>Date</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {depenses.map((depense) => (
                  <tr key={depense.idDepense}>
                    <td>{depense.idDepense}</td>
                    <td>{depense.idProjet}</td>
                    <td>{depense.montant.toFixed(2)}</td>
                    <td>{depense.categorie}</td>
                    <td>{depense.statut}</td>
                    <td>{new Date(depense.dateDepense).toLocaleDateString()}</td>
                    <td>
                      <button className="btn btn-danger" onClick={() => handleDeleteDepense(depense.idDepense)}>
                        Supprimer
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}
    </div>
  );
}

