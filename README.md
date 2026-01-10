# Social Network Backend
Arquitectura Hexagonal • Spring Boot 3.2.1 • JPA/Hibernate • Liquibase • Testcontainers • Multipass

Este proyecto implementa un backend profesional para una red social, siguiendo principios de arquitectura limpia, separación estricta de capas, pruebas de integración reales y un flujo de trabajo basado en capítulos (features) para aprendizaje, escalabilidad y mantenibilidad.

---

# 🚀 Tecnologías principales

- **Java 21**
- **Spring Boot 3.2.1**
- **Arquitectura Hexagonal (Ports & Adapters)**
- **Spring Security 6**
- **JPA/Hibernate 6.4+**
- **Liquibase** (migraciones de base de datos)
- **PostgreSQL**
- **Testcontainers** (tests de integración reales)
- **Multipass** (backend de Docker en Windows)
- **JUnit 6 + AssertJ**
- **Maven**

---

# 🧱 Arquitectura del proyecto

src/
├── main/
│   ├── java/com/example/socialnetwork/
│   │   ├── domain/          → Reglas de negocio (entidades, VOs, repositorios)
│   │   ├── application/     → Casos de uso (servicios)
│   │   ├── infrastructure/  → Adaptadores (JPA, persistencia, mappers, seguridad)
│   │   └── delivery/        → Controladores REST y DTOs
│   └── resources/
│       ├── application.yml
│       └── db/changelog/    → Migraciones Liquibase
└── test/
├── java/...             → Tests unitarios y de integración
└── resources/
└── application-test.yml


La arquitectura sigue el patrón **Hexagonal / Ports & Adapters**, donde:

- **domain** contiene la lógica de negocio pura
- **application** orquesta casos de uso
- **infrastructure** implementa adaptadores (JPA, seguridad, mappers…)
- **delivery** expone la API REST

---

# 🔐 Seguridad (Producción vs Tests)

El proyecto separa completamente la seguridad entre entornos:

### ✔ Seguridad en producción (`SecurityConfig`)
- Solo se carga en perfiles distintos de `test`
- Usa **BCryptPasswordEncoder**
- Requiere autenticación básica
- Protege todos los endpoints excepto `/actuator/health`

### ✔ Seguridad en tests (`TestSecurityConfig`)
- Solo se carga en tests
- Define usuarios en memoria:
    - `admin/password`
    - `test@example.com/password123`
- Desactiva CSRF
- Usa **NoOpPasswordEncoder**
- Facilita pruebas sin fricción

### ✔ Exclusión explícita en WebMvcTest
Los slice tests excluyen la seguridad real:

```java
excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = SecurityConfig.class
)
Esto garantiza que los tests no dependan de la seguridad de producción.

🧪 Tests de integración con Testcontainers
Los tests usan una base de datos PostgreSQL real mediante Testcontainers:

java
@Container
static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15-alpine");
Liquibase se ejecuta automáticamente en cada test gracias a:

java
@DynamicPropertySource
static void configure(DynamicPropertyRegistry registry) { ... }
✔ Beneficios
Tests reproducibles

Aislados del entorno local

Sin necesidad de instalar PostgreSQL

🖥️ Multipass como backend de Docker (Windows)
Para evitar problemas con Docker Desktop en Windows, el proyecto soporta ejecutar Testcontainers usando Multipass:

1. Crear VM Ubuntu
bash
multipass launch --name docker-vm --cpus 4 --memory 6G --disk 30G
2. Instalar Docker dentro de la VM
bash
multipass shell docker-vm
sudo apt update
sudo apt install -y docker.io
sudo usermod -aG docker $USER
exit
multipass shell docker-vm
3. Exponer Docker Engine por TCP
Editar:

Código
/lib/systemd/system/docker.service
Cambiar:

Código
ExecStart=/usr/bin/dockerd -H fd://
Por:

Código
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375
Reiniciar:

bash
sudo systemctl daemon-reload
sudo systemctl restart docker
4. Configurar Windows
bash
setx DOCKER_HOST "tcp://<IP-de-la-VM>:2375"
5. Validar
bash
docker ps
Si responde → Testcontainers usará esta VM automáticamente.

🧪 Base de datos
El proyecto usa Liquibase para gestionar el esquema.

Migraciones:

Código
db/changelog/db.changelog-master.yaml
db/changelog/001-create-users-table.yaml
...
Ejemplo de tabla:

Código
users (
  id VARCHAR(50) PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  display_name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
)
📦 Funcionalidades implementadas por capítulos
Capítulos 1–4
Configuración inicial

Arquitectura hexagonal

Entidades de dominio (User, UserId, UserEmail)

Capítulo 5 — Persistencia
Adaptador JPA

Entidad UserEntity

Migraciones Liquibase

Testcontainers + PostgreSQL real

Capítulo 6 — Manejo global de errores
ApiError

ApiErrorHandler

Excepciones de dominio

Tests de integración

Capítulo 7+
Sistema de follows

Publicaciones

Comentarios

Seguridad

Tests de integración avanzados

▶️ Ejecución del proyecto
Modo normal
bash
mvn spring-boot:run
Ejecutar tests
bash
mvn clean test
🌱 Flujo de trabajo Git por capítulos
Cada capítulo se desarrolla en una rama:

Código
feature/05-persistence
feature/06-error-handling
feature/07-follow-system
feature/18-openapi
...
Las reparaciones se gestionan en ramas:

Código
fix/testing-security-and-testcontainers
Y se fusionan mediante Pull Requests hacia develop.

📄 Licencia
Proyecto educativo. Uso libre.



