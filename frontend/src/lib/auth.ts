import api from './api';
import Cookies from 'js-cookie';

export interface LoginRequest {
  email: string;
  motDePasse: string;
}

export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  roleId: number;
}

export interface AuthResponse {
  token: string;
  utilisateur: {
    idUser: number;
    nom: string;
    prenom: string;
    email: string;
    role: {
      idRole: number;
      nomRole: string;
    };
  };
}

export const authService = {
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await api.post('/api/auth/login', data);
    if (response.data.token) {
      Cookies.set('token', response.data.token, { expires: 7 });
    }
    return response.data;
  },

  register: async (data: RegisterRequest): Promise<AuthResponse> => {
    const response = await api.post('/api/auth/register', data);
    if (response.data.token) {
      Cookies.set('token', response.data.token, { expires: 7 });
    }
    return response.data;
  },

  logout: () => {
    Cookies.remove('token');
    window.location.href = '/login';
  },

  isAuthenticated: (): boolean => {
    return !!Cookies.get('token');
  },
};

