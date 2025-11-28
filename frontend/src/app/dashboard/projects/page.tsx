'use client';

import { useEffect, useState } from 'react';
import { projectsService, Projet, ProjetRequest } from '@/lib/projects';
import { toast } from 'react-toastify';
import { useForm } from 'react-hook-form';

export default function ProjectsPage() {
  const [projects, setProjects] = useState<Projet[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingProject, setEditingProject] = useState<Projet | null>(null);
  const { register, handleSubmit, reset, formState: { errors } } = useForm<ProjetRequest>();

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    try {
      const data = await projectsService.getAll();
      setProjects(data);
    } catch (error: any) {
      toast.error('Erreur lors du chargement des projets');
    } finally {
      setLoading(false);
    }
  };

  const onSubmit = async (data: ProjetRequest) => {
    try {
      if (editingProject) {
        await projectsService.update(editingProject.idProjet, data);
        toast.success('Projet mis à jour avec succès');
      } else {
        await projectsService.create(data);
        toast.success('Projet créé avec succès');
      }
      reset();
      setShowForm(false);
      setEditingProject(null);
      fetchProjects();
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Erreur lors de la sauvegarde');
    }
  };

  const handleEdit = (project: Projet) => {
    setEditingProject(project);
    reset({
      nomProjet: project.nomProjet,
      description: project.description,
      chefProjetId: project.chefProjetId,
      statut: project.statut,
      dateDebut: project.dateDebut?.split('T')[0],
      dateFinPrevue: project.dateFinPrevue?.split('T')[0],
      budgetAlloue: project.budgetAlloue,
      departement: project.departement,
      priorite: project.priorite,
    });
    setShowForm(true);
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce projet?')) return;
    try {
      await projectsService.delete(id);
      toast.success('Projet supprimé avec succès');
      fetchProjects();
    } catch (error: any) {
      toast.error('Erreur lors de la suppression');
    }
  };

  if (loading) {
    return <div className="container">Chargement...</div>;
  }

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <h1>Gestion des Projets</h1>
        <button className="btn btn-primary" onClick={() => { setShowForm(!showForm); setEditingProject(null); reset(); }}>
          {showForm ? 'Annuler' : 'Nouveau Projet'}
        </button>
      </div>

      {showForm && (
        <div className="card" style={{ marginBottom: '20px' }}>
          <h2>{editingProject ? 'Modifier le projet' : 'Nouveau projet'}</h2>
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="form-group">
              <label>Nom du projet *</label>
              <input {...register('nomProjet', { required: 'Nom requis' })} />
              {errors.nomProjet && <span style={{ color: 'red' }}>{errors.nomProjet.message}</span>}
            </div>
            <div className="form-group">
              <label>Description</label>
              <textarea {...register('description')} />
            </div>
            <div className="form-group">
              <label>Chef de projet ID *</label>
              <input type="number" {...register('chefProjetId', { required: 'Chef de projet requis', valueAsNumber: true })} />
              {errors.chefProjetId && <span style={{ color: 'red' }}>{errors.chefProjetId.message}</span>}
            </div>
            <div className="form-group">
              <label>Statut</label>
              <select {...register('statut')}>
                <option value="EN_PLANIFICATION">En planification</option>
                <option value="EN_COURS">En cours</option>
                <option value="EN_PAUSE">En pause</option>
                <option value="TERMINE">Terminé</option>
                <option value="ANNULE">Annulé</option>
              </select>
            </div>
            <div className="form-group">
              <label>Date de début</label>
              <input type="date" {...register('dateDebut')} />
            </div>
            <div className="form-group">
              <label>Date de fin prévue</label>
              <input type="date" {...register('dateFinPrevue')} />
            </div>
            <div className="form-group">
              <label>Budget alloué</label>
              <input type="number" step="0.01" {...register('budgetAlloue', { valueAsNumber: true })} />
            </div>
            <div className="form-group">
              <label>Département</label>
              <input {...register('departement')} />
            </div>
            <div className="form-group">
              <label>Priorité</label>
              <select {...register('priorite')}>
                <option value="BASSE">Basse</option>
                <option value="MOYENNE">Moyenne</option>
                <option value="HAUTE">Haute</option>
                <option value="CRITIQUE">Critique</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary">
              {editingProject ? 'Mettre à jour' : 'Créer'}
            </button>
          </form>
        </div>
      )}

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nom</th>
              <th>Statut</th>
              <th>Priorité</th>
              <th>Budget</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {projects.map((project) => (
              <tr key={project.idProjet}>
                <td>{project.idProjet}</td>
                <td>{project.nomProjet}</td>
                <td>{project.statut}</td>
                <td>{project.priorite}</td>
                <td>{project.budgetAlloue?.toFixed(2) || 'N/A'}</td>
                <td>
                  <button className="btn btn-secondary" onClick={() => handleEdit(project)} style={{ marginRight: '5px' }}>
                    Modifier
                  </button>
                  <button className="btn btn-danger" onClick={() => handleDelete(project.idProjet)}>
                    Supprimer
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

