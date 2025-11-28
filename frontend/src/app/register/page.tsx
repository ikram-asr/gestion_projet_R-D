'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { authService, RegisterRequest } from '@/lib/auth';
import { toast } from 'react-toastify';
import Link from 'next/link';

export default function RegisterPage() {
  const router = useRouter();
  const { register, handleSubmit, formState: { errors } } = useForm<RegisterRequest>();
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data: RegisterRequest) => {
    setLoading(true);
    try {
      await authService.register({ ...data, roleId: 2 }); // Default role: RESEARCHER
      toast.success('Inscription réussie!');
      router.push('/dashboard');
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Erreur d\'inscription');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{ maxWidth: '400px', marginTop: '50px' }}>
      <div className="card">
        <h1 style={{ marginBottom: '20px' }}>Inscription</h1>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="form-group">
            <label>Nom</label>
            <input
              {...register('nom', { required: 'Nom requis' })}
            />
            {errors.nom && <span style={{ color: 'red' }}>{errors.nom.message}</span>}
          </div>
          <div className="form-group">
            <label>Prénom</label>
            <input
              {...register('prenom', { required: 'Prénom requis' })}
            />
            {errors.prenom && <span style={{ color: 'red' }}>{errors.prenom.message}</span>}
          </div>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              {...register('email', { required: 'Email requis' })}
            />
            {errors.email && <span style={{ color: 'red' }}>{errors.email.message}</span>}
          </div>
          <div className="form-group">
            <label>Mot de passe</label>
            <input
              type="password"
              {...register('motDePasse', { required: 'Mot de passe requis', minLength: 6 })}
            />
            {errors.motDePasse && <span style={{ color: 'red' }}>{errors.motDePasse.message}</span>}
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading} style={{ width: '100%' }}>
            {loading ? 'Inscription...' : 'S\'inscrire'}
          </button>
        </form>
        <p style={{ marginTop: '15px', textAlign: 'center' }}>
          Déjà un compte? <Link href="/login">Se connecter</Link>
        </p>
      </div>
    </div>
  );
}

