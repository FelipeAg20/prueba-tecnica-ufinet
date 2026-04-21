# Gestión de Autos — Prueba Técnica

Aplicación full-stack para registro y gestión de autos por usuario autenticado.

**Stack:** Spring Boot 3 + JWT + Spring Security | React 18 + Vite | PostgreSQL

---

## Requisitos previos

| Herramienta | Versión mínima |
|-------------|----------------|
| Java        | 21             |
| Maven       | 3.9+           |
| Node.js     | 18+            |
| PostgreSQL  | 14+            |

---

## 1. Base de datos

Crea la base de datos (solo una vez):

```bash
createdb -U postgres autos_db
```

Las tablas se crean automáticamente al levantar el backend. También se insertan datos de muestra listos para probar:

| Campo    | Valor              |
|----------|--------------------|
| Email    | carlos@example.com |
| Password | password123        |

---

## 2. Backend

Edita `backend/src/main/resources/application.properties` si tus credenciales de PostgreSQL son distintas:

```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Luego levanta:

```bash
cd backend
mvn spring-boot:run
```

El servidor arranca en `http://localhost:8080`.

---

## 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

La app arranca en `http://localhost:5173`.

---

## Endpoints de la API

| Método | Ruta                | Descripción              | Auth |
|--------|---------------------|--------------------------|------|
| POST   | `/api/auth/register`| Registro de usuario      | No   |
| POST   | `/api/auth/login`   | Login — devuelve JWT     | No   |
| GET    | `/api/cars`         | Listar autos del usuario | Sí   |
| GET    | `/api/cars/search`  | Buscar/filtrar autos     | Sí   |
| POST   | `/api/cars`         | Crear auto               | Sí   |
| PUT    | `/api/cars/{id}`    | Editar auto              | Sí   |
| DELETE | `/api/cars/{id}`    | Eliminar auto            | Sí   |

### Búsqueda — query params opcionales

```
GET /api/cars/search?plate=ABC&model=corolla&brand=Toyota&year=2022
```

### Ejemplo de login

```json
POST /api/auth/login
{
  "email": "carlos@example.com",
  "password": "password123"
}
```

Respuesta:

```json
{
  "token": "eyJ...",
  "name": "Carlos Ruiz",
  "email": "carlos@example.com"
}
```
