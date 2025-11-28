# ERP R&D - Système de Gestion de Projets

Architecture microservices avec Spring Boot et Next.js

## Services

- **Auth Service** (Port 8081) - Authentification et gestion des utilisateurs
- **Projects Service** (Port 8083) - Gestion des projets R&D
- **Validation Service** (Port 8082) - Gestion des tests scientifiques
- **Finance Service** (Port 8084) - Gestion des budgets et dépenses
- **Gateway Service** (Port 8080) - API Gateway avec routage et sécurité JWT

## Base de données

- PostgreSQL 15 pour chaque service
- pgAdmin 17 (Port 5050) - Interface d'administration

## Technologies

### Backend
- Spring Boot 3.2.0
- PostgreSQL 15
- RabbitMQ
- JWT pour l'authentification
- MapStruct pour le mapping

### Frontend
- Next.js 14
- TypeScript
- React Hook Form
- Axios
- React Toastify

## Démarrage avec Docker

```bash
docker-compose up -d
```

## Accès aux services

- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **pgAdmin**: http://localhost:5050
  - Email: admin@admin.com
  - Password: admin
- **RabbitMQ Management**: http://localhost:15672
  - Username: admin
  - Password: admin

## Configuration des bases de données dans pgAdmin

### Auth DB
- Host: auth-db
- Port: 5432
- Database: auth_db
- Username: auth_user
- Password: auth_password

### Validation DB
- Host: validation-db
- Port: 5432
- Database: validation_db
- Username: validation_user
- Password: validation_password

### Projects DB
- Host: projects-db
- Port: 5432
- Database: projects_db
- Username: projects_user
- Password: projects_password

### Finance DB
- Host: finance-db
- Port: 5432
- Database: finance_db
- Username: finance_user
- Password: finance_password

## Développement local

### Backend

Chaque service peut être lancé individuellement avec Maven:

```bash
cd auth-service
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

## API Endpoints

### Auth
- POST `/api/auth/register` - Inscription
- POST `/api/auth/login` - Connexion

### Projects
- GET `/api/projects` - Liste des projets
- GET `/api/projects/{id}` - Détails d'un projet
- POST `/api/projects` - Créer un projet
- PUT `/api/projects/{id}` - Modifier un projet
- DELETE `/api/projects/{id}` - Supprimer un projet

### Finance
- GET `/api/finance/budgets` - Liste des budgets
- POST `/api/finance/budgets` - Créer un budget
- GET `/api/finance/depenses` - Liste des dépenses
- POST `/api/finance/depenses` - Créer une dépense

### Validation
- GET `/api/validations/tests` - Liste des tests
- POST `/api/validations/tests` - Créer un test

## Notes

- Tous les endpoints (sauf `/api/auth/**`) nécessitent un token JWT dans le header `Authorization: Bearer <token>`
- Les fichiers de configuration utilisent `application.properties` (pas YAML)
- Tous les services ont des opérations CRUD complètes

