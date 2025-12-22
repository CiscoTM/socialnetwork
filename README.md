# Social Network Backend
Arquitectura Hexagonal • Spring Boot 4 • JPA/Hibernate • Liquibase • Testcontainers

Este proyecto implementa la base de un backend profesional para una red social, siguiendo principios de arquitectura limpia, separación de capas, pruebas de integración reales y un flujo de trabajo orientado a capítulos (features) para aprendizaje y escalabilidad.

---

## 🚀 Tecnologías principales

- **Java 21**
- **Spring Boot 4**
- **Arquitectura Hexagonal (Ports & Adapters)**
- **JPA/Hibernate 7**
- **Liquibase** (migraciones de base de datos)
- **PostgreSQL**
- **Testcontainers** (tests de integración reales)
- **JUnit 6 + AssertJ**
- **Maven**

---

## 🧱 Arquitectura del proyecto
src/
├── main/
│    ├── java/com/example/socialnetwork/
│    │     ├── domain/        → Reglas de negocio (entidades, VOs, repositorios)
│    │     ├── application/   → Casos de uso (servicios)
│    │     ├── infrastructure/→ Adaptadores (JPA, persistencia, mappers)
│    │     └── delivery/      → Controladores REST y DTOs
│    └── resources/
│          ├── application.yml
│          └── db/changelog/  → Migraciones Liquibase
└── test/
├── java/...            → Tests de integración y unitarios
└── resources/
└── application-test.yml


---

## 📦 Funcionalidades implementadas por capítulos

### **Capítulo 1–4**
- Configuración inicial del proyecto
- Arquitectura hexagonal
- Entidades de dominio (`User`, `UserId`, `UserEmail`)
- Repositorios de dominio

### **Capítulo 5 — Persistencia**
- Implementación de persistencia con JPA/Hibernate
- Adaptador `UserRepositoryJpaAdapter`
- Entidad `UserEntity`
- Migraciones Liquibase (`users` table)
- Testcontainers + PostgreSQL real para tests

### **Capítulo 6 — Manejo global de errores**
- `ApiError` (DTO estándar de error)
- `ApiErrorHandler` con `@RestControllerAdvice`
- Excepciones de dominio:
    - `UserAlreadyExistsException`
    - `InvalidEmailException`
    - `InvalidDisplayNameException`
- Tests de integración para errores 400 y 409

---

## 🗄️ Base de datos

El proyecto usa **Liquibase** para gestionar el esquema.

Migración principal:

db/changelog/db.changelog-master.yaml
db/changelog/001-create-users-table.yaml


Tabla creada:

users (
id VARCHAR(50) PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
display_name VARCHAR(255) NOT NULL,
created_at TIMESTAMP NOT NULL
)


---

## 🧪 Tests

Los tests de integración usan:

- **Testcontainers** para levantar PostgreSQL real
- **Liquibase** ejecutado automáticamente en cada test
- `IntegrationTestBase` para inicializar el contenedor antes del contexto de Spring

Ejemplo:

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerErrorHandlingIntegrationTest extends IntegrationTestBase { ... }

▶️ Ejecución del proyecto
Modo normal
mvn spring-boot:run
Ejecutar tests
mvn clean test

🌱 Flujo de trabajo por capítulos (Git)
Cada capítulo se desarrolla en una rama:

feature/chapter-5-persistence
feature/chapter-6-error-handling
feature/chapter-7-follow-system
...
Y se fusiona en develop mediante Pull Requests.

📄 Licencia
Proyecto educativo. Uso libre.