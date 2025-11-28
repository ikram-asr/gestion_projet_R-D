'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { authService, LoginRequest } from '@/lib/auth';
import { toast } from 'react-toastify';
import Link from 'next/link';

export default function LoginPage() {
  const router = useRouter();
  const { register, handleSubmit, formState: { errors } } = useForm<LoginRequest>();
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data: LoginRequest) => {
    setLoading(true);
    try {
      await authService.login(data);
      toast.success('Connexion réussie!');
      router.push('/dashboard');
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Erreur de connexion');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{ maxWidth: '400px', marginTop: '100px' }}>
      <div className="card">
        <h1 style={{ marginBottom: '20px' }}>Connexion</h1>
        <form onSubmit={handleSubmit(onSubmit)}>
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
              {...register('motDePasse', { required: 'Mot de passe requis' })}
            />
            {errors.motDePasse && <span style={{ color: 'red' }}>{errors.motDePasse.message}</span>}
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading} style={{ width: '100%' }}>
            {loading ? 'Connexion...' : 'Se connecter'}
          </button>
        </form>
        <p style={{ marginTop: '15px', textAlign: 'center' }}>
          Pas de compte? <Link href="/register">S&apos;inscrire</Link>
        </p>
      </div>
    </div>
  );
}

