'use client';

import { useEffect, useState } from 'react';
import { validationService, TestScientifique, TestScientifiqueRequest } from '@/lib/validation';
import { toast } from 'react-toastify';
import { useForm } from 'react-hook-form';

export default function ValidationPage() {
  const [tests, setTests] = useState<TestScientifique[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const { register, handleSubmit, reset, formState: { errors } } = useForm<TestScientifiqueRequest>();

  useEffect(() => {
    fetchTests();
  }, []);

  const fetchTests = async () => {
    try {
      const data = await validationService.getAllTests();
      setTests(data);
    } catch (error: any) {
      toast.error('Erreur lors du chargement des tests');
    } finally {
      setLoading(false);
    }
  };

  const onSubmit = async (data: TestScientifiqueRequest) => {
    try {
      await validationService.createTest(data);
      toast.success('Test créé avec succès');
      reset();
      setShowForm(false);
      fetchTests();
    } catch (error: any) {
      toast.error('Erreur lors de la création');
    }
  };

  const handleUpdateStatut = async (id: number, statut: TestScientifique['statut']) => {
    try {
      await validationService.updateTestStatut(id, statut);
      toast.success('Statut mis à jour');
      fetchTests();
    } catch (error) {
      toast.error('Erreur lors de la mise à jour');
    }
  };

  if (loading) {
    return <div className="container">Chargement...</div>;
  }

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <h1>Gestion des Tests Scientifiques</h1>
        <button className="btn btn-primary" onClick={() => { setShowForm(!showForm); reset(); }}>
          {showForm ? 'Annuler' : 'Nouveau Test'}
        </button>
      </div>

      {showForm && (
        <div className="card" style={{ marginBottom: '20px' }}>
          <h2>Nouveau test scientifique</h2>
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="form-group">
              <label>ID Projet *</label>
              <input type="number" {...register('idProjet', { required: 'ID Projet requis', valueAsNumber: true })} />
              {errors.idProjet && <span style={{ color: 'red' }}>{errors.idProjet.message}</span>}
            </div>
            <div className="form-group">
              <label>Nom du test *</label>
              <input {...register('nomTest', { required: 'Nom requis' })} />
              {errors.nomTest && <span style={{ color: 'red' }}>{errors.nomTest.message}</span>}
            </div>
            <div className="form-group">
              <label>Description</label>
              <textarea {...register('description')} />
            </div>
            <div className="form-group">
              <label>Type de test</label>
              <input {...register('typeTest')} />
            </div>
            <div className="form-group">
              <label>Responsable ID</label>
              <input type="number" {...register('responsableTestId', { valueAsNumber: true })} />
            </div>
            <div className="form-group">
              <label>Statut</label>
              <select {...register('statut')}>
                <option value="EN_ATTENTE">En attente</option>
                <option value="EN_COURS">En cours</option>
                <option value="TERMINE">Terminé</option>
                <option value="ANNULE">Annulé</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary">Créer</button>
          </form>
        </div>
      )}

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Projet ID</th>
              <th>Nom</th>
              <th>Type</th>
              <th>Statut</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {tests.map((test) => (
              <tr key={test.idTest}>
                <td>{test.idTest}</td>
                <td>{test.idProjet}</td>
                <td>{test.nomTest}</td>
                <td>{test.typeTest || 'N/A'}</td>
                <td>{test.statut}</td>
                <td>
                  <select
                    value={test.statut}
                    onChange={(e) => handleUpdateStatut(test.idTest, e.target.value as TestScientifique['statut'])}
                    className="btn btn-secondary"
                  >
                    <option value="EN_ATTENTE">En attente</option>
                    <option value="EN_COURS">En cours</option>
                    <option value="TERMINE">Terminé</option>
                    <option value="ANNULE">Annulé</option>
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

