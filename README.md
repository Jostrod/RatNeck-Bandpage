# RatNeck-Bandpage
Website for the Norwegian rock band RatNeck, where I play drums. The site will include a concert overview, a contact page and a merch store. The backend is a Spring Boot REST API and the frontend is built with Nuxt.

## Tech Stack

### Backend
- Java 21, Spring Boot, Maven
- Spring Web, Spring Data JPA, Spring Security, Bean Validation
- H2 (in-memory database) – MySQL planned
- Lombok

### Frontend
- Nuxt 3, Vue 3, Tailwind CSS
- Node v24

### Testing
- JUnit 5, Mockito

## Status
The concert API is operational. The frontend is in progress.

### Backend
- [x] Concert CRUD API
- [x] Input validation and error handling
- [x] Unit and integration tests
- [x] CORS configuration
- [x] Session-based admin login (Spring Security) – public GET, protected write operations
- [ ] Persistent database (MySQL)
- [ ] CSRF protection for production

### Frontend
- [ ] Concert overview and admin pages (in progress)

### Planned
- [ ] Merch store with Stripe payments
- [ ] Deployment

## Architecture
```
HTTP Request
    │
    ▼
Security    ──── public GET, login required for write operations
    │
    ▼
Controller  ──── validates input (@Valid), maps HTTP to method calls
    │
    ▼
Service     ──── business logic, DTO <-> entity conversion
    │
    ▼
Repository  ──── database access (Spring Data JPA)
    │
    ▼
H2 Database (MySQL planned)
```

## API Endpoints
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | /api/concerts | Get all concerts | Public |
| GET | /api/concerts/{id} | Get concert by id | Public |
| POST | /api/concerts | Create a concert | Admin |
| PUT | /api/concerts/{id} | Update a concert | Admin |
| DELETE | /api/concerts/{id} | Delete a concert | Admin |
| GET | /api/merch | Get all merch | Public |
| GET | /api/merch/{id} | Get merch item by id | Public |
| POST | /api/merch | Create a merch item | Admin |
| PUT | /api/merch/{id} | Update a merch item | Admin |
| DELETE | /api/merch/{id} | Delete a merch item | Admin |
| POST | /login | Log in (form: username, password) | Public |
| POST | /logout | Log out | Admin |

## Getting Started

> **Note:** The backend requires two environment variables:
> - `DB_PASSWORD` – password for the H2 database
> - `ADMIN_PASSWORD` – password for the `admin` user
>
> Set them before running, e.g. via your IDE's run configuration.

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
Runs on `http://localhost:8080`. The H2 console is available at `/h2-console`.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Runs on `http://localhost:3000`
