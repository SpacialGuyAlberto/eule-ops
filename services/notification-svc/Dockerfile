FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY . .

RUN apk add --no-cache maven
RUN mvn -f services/notification-svc/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/services/notification-svc/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
