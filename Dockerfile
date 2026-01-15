# ============================
# Stage 1 - Build
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiamos el pom y descargamos dependencias para cachear
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Construimos el JAR (sin tests)
RUN mvn -q -e -B clean package -DskipTests


# ============================
# Stage 2 - Runtime
# ============================
FROM eclipse-temurin:21-jre AS runtime

# Instalamos curl para el healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Creamos usuario no root
RUN useradd -m appuser

WORKDIR /app

# Copiamos el JAR desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Healthcheck nativo de Docker
HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Ejecutamos como usuario no root
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
