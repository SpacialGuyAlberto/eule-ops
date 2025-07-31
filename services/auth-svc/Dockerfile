# 🔨 Etapa de build
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY . .

# ⚠️ Compila el client y auth-svc (asegúrate del orden)
RUN mvn -pl clients/notification-client,services/auth-svc -am clean install -DskipTests

# 🚀 Etapa final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el jar generado desde el builder
COPY --from=builder /app/services/auth-svc/target/auth-svc-*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
