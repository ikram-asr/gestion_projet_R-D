import api from './api';

export interface Budget {
  idBudget: number;
  idProjet: number;
  montantAlloue: number;
  montantDepense: number;
  anneeBudgetaire: number;
  description?: string;
  statut: 'ACTIF' | 'CLOTURE' | 'ANNULE';
  createdAt: string;
  updatedAt: string;
}

export interface BudgetRequest {
  idProjet: number;
  montantAlloue: number;
  anneeBudgetaire: number;
  description?: string;
  statut?: Budget['statut'];
}

export interface Depense {
  idDepense: number;
  idProjet: number;
  idBudget?: number;
  montant: number;
  description?: string;
  categorie: 'EQUIPEMENT' | 'MATERIEL' | 'SALAIRES' | 'FORMATION' | 'DEPLACEMENT' | 'AUTRE';
  dateDepense: string;
  approuveParId?: number;
  statut: 'EN_ATTENTE' | 'APPROUVE' | 'REJETE' | 'REMBOURSE';
  justificatif?: string;
  createdAt: string;
  updatedAt: string;
}

export interface DepenseRequest {
  idProjet: number;
  idBudget?: number;
  montant: number;
  description?: string;
  categorie?: Depense['categorie'];
  dateDepense?: string;
  approuveParId?: number;
  statut?: Depense['statut'];
  justificatif?: string;
}

export const financeService = {
  // Budgets
  getAllBudgets: async (): Promise<Budget[]> => {
    const response = await api.get('/api/finance/budgets');
    return response.data;
  },

  getBudgetById: async (id: number): Promise<Budget> => {
    const response = await api.get(`/api/finance/budgets/${id}`);
    return response.data;
  },

  createBudget: async (data: BudgetRequest): Promise<Budget> => {
    const response = await api.post('/api/finance/budgets', data);
    return response.data;
  },

  updateBudget: async (id: number, data: BudgetRequest): Promise<Budget> => {
    const response = await api.put(`/api/finance/budgets/${id}`, data);
    return response.data;
  },

  deleteBudget: async (id: number): Promise<void> => {
    await api.delete(`/api/finance/budgets/${id}`);
  },

  getBudgetsByProjet: async (idProjet: number): Promise<Budget[]> => {
    const response = await api.get(`/api/finance/budgets/projet/${idProjet}`);
    return response.data;
  },

  // Depenses
  getAllDepenses: async (): Promise<Depense[]> => {
    const response = await api.get('/api/finance/depenses');
    return response.data;
  },

  getDepenseById: async (id: number): Promise<Depense> => {
    const response = await api.get(`/api/finance/depenses/${id}`);
    return response.data;
  },

  createDepense: async (data: DepenseRequest): Promise<Depense> => {
    const response = await api.post('/api/finance/depenses', data);
    return response.data;
  },

  updateDepense: async (id: number, data: DepenseRequest): Promise<Depense> => {
    const response = await api.put(`/api/finance/depenses/${id}`, data);
    return response.data;
  },

  deleteDepense: async (id: number): Promise<void> => {
    await api.delete(`/api/finance/depenses/${id}`);
  },

  getDepensesByProjet: async (idProjet: number): Promise<Depense[]> => {
    const response = await api.get(`/api/finance/depenses/projet/${idProjet}`);
    return response.data;
  },

  getDepensesByBudget: async (idBudget: number): Promise<Depense[]> => {
    const response = await api.get(`/api/finance/depenses/budget/${idBudget}`);
    return response.data;
  },

  updateDepenseStatut: async (id: number, statut: Depense['statut'], approuveParId?: number): Promise<Depense> => {
    const response = await api.put(`/api/finance/depenses/${id}/statut?statut=${statut}${approuveParId ? `&approuveParId=${approuveParId}` : ''}`);
    return response.data;
  },
};

