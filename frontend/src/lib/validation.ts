import api from './api';

export interface TestScientifique {
  idTest: number;
  idProjet: number;
  nomTest: string;
  description?: string;
  typeTest?: string;
  responsableTestId?: number;
  statut: 'EN_ATTENTE' | 'EN_COURS' | 'TERMINE' | 'ANNULE';
  dateDebut?: string;
  dateFin?: string;
  createdAt: string;
  updatedAt: string;
}

export interface TestScientifiqueRequest {
  idProjet: number;
  nomTest: string;
  description?: string;
  typeTest?: string;
  responsableTestId?: number;
  statut?: TestScientifique['statut'];
  dateDebut?: string;
  dateFin?: string;
}

export const validationService = {
  getAllTests: async (): Promise<TestScientifique[]> => {
    const response = await api.get('/api/validations/tests');
    return response.data;
  },

  getTestById: async (id: number): Promise<TestScientifique> => {
    const response = await api.get(`/api/validations/tests/${id}`);
    return response.data;
  },

  createTest: async (data: TestScientifiqueRequest): Promise<TestScientifique> => {
    const response = await api.post('/api/validations/tests', data);
    return response.data;
  },

  getTestsByProjet: async (idProjet: number): Promise<TestScientifique[]> => {
    const response = await api.get(`/api/validations/tests/projet/${idProjet}`);
    return response.data;
  },

  updateTestStatut: async (id: number, statut: TestScientifique['statut']): Promise<TestScientifique> => {
    const response = await api.put(`/api/validations/tests/${id}/statut?statut=${statut}`);
    return response.data;
  },
};

