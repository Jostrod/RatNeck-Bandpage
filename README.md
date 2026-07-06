# RatNeck-Bandpage
Website built for the norwegian rock band RatNeck. The site will include a merch store, contact site and a concert-management-system for the band. 

## Tech Stack

### Backend:
- Java 21, Spring Boot, Maven
- Spring Web, Spring Data JPA
- H2 (in-memory database)
- Lombok


### Frontend
- Nuxt, Vue
- Node v24

### Testing
- JUnit 5, Mockito


## Status
Backend mostly operational. Non-essentials at this point like Authentication and Swagger are not yet set up. CORS is configured to allow frontend-backend communication.
### Backend
- [x] Concert CRUD API
- [x] Input validation and error handling
- [x] Unit and integration tests
- [x] CORS configuration
- [ ] Authentication
- [ ] Persistent database (currently H2 in-memory)

### Frontend
- [ ] Not started

## Architecture
```
HTTP Request
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
H2 Database
```
## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/concerts | Get all concerts |
| GET | /api/concerts/{id} | Get concert by id |
| POST | /api/concerts | Create a concert |
| PUT | /api/concerts/{id} | Update a concert |
| DELETE | /api/concerts/{id} | Delete a concert |

## Getting Started

> **Note:** The backend reads the database password from a `DB_PASSWORD` 
> environment variable (referenced as `${DB_PASSWORD}` in application.properties). 
> Set it before running, e.g. via your IDE's run configuration.

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
Runs on `http://localhost:8080`

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Runs on `http://localhost:3000`


