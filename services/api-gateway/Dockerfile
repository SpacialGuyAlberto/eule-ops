FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY . .

RUN apk add --no-cache maven
RUN mvn -f services/api-gateway/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/services/api-gateway/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
