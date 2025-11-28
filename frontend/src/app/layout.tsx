import './globals.css';
import { Inter } from 'next/font/google';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const inter = Inter({ subsets: ['latin'] });

export const metadata = {
  title: 'ERP R&D - Gestion de Projets',
  description: 'Système de gestion de projets R&D',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="fr">
      <body className={inter.className}>
        {children}
        <ToastContainer position="top-right" autoClose={3000} />
      </body>
    </html>
  );
}

