import api from './api';

export interface Projet {
  idProjet: number;
  nomProjet: string;
  description?: string;
  chefProjetId: number;
  statut: 'EN_PLANIFICATION' | 'EN_COURS' | 'EN_PAUSE' | 'TERMINE' | 'ANNULE';
  dateDebut?: string;
  dateFinPrevue?: string;
  dateFinReelle?: string;
  budgetAlloue?: number;
  departement?: string;
  priorite: 'BASSE' | 'MOYENNE' | 'HAUTE' | 'CRITIQUE';
  createdAt: string;
  updatedAt: string;
}

export interface ProjetRequest {
  nomProjet: string;
  description?: string;
  chefProjetId: number;
  statut?: Projet['statut'];
  dateDebut?: string;
  dateFinPrevue?: string;
  budgetAlloue?: number;
  departement?: string;
  priorite?: Projet['priorite'];
}

export const projectsService = {
  getAll: async (): Promise<Projet[]> => {
    const response = await api.get('/api/projects');
    return response.data;
  },

  getById: async (id: number): Promise<Projet> => {
    const response = await api.get(`/api/projects/${id}`);
    return response.data;
  },

  create: async (data: ProjetRequest): Promise<Projet> => {
    const response = await api.post('/api/projects', data);
    return response.data;
  },

  update: async (id: number, data: ProjetRequest): Promise<Projet> => {
    const response = await api.put(`/api/projects/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/api/projects/${id}`);
  },

  getByChefProjet: async (chefProjetId: number): Promise<Projet[]> => {
    const response = await api.get(`/api/projects/chef/${chefProjetId}`);
    return response.data;
  },

  getByStatut: async (statut: Projet['statut']): Promise<Projet[]> => {
    const response = await api.get(`/api/projects/statut/${statut}`);
    return response.data;
  },

  updateStatut: async (id: number, statut: Projet['statut']): Promise<Projet> => {
    const response = await api.put(`/api/projects/${id}/statut?statut=${statut}`);
    return response.data;
  },
};

