# Stage 1: Build stage (Render khud JAR banayega)
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# Maven clean package se target folder cloud par hi banega
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Stage 2: Run stage (Sirf JAR ko chalayenge)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Notification service ke liye port 8083 (ya jo aapne code mein set kiya ho)
EXPOSE 8083 
ENTRYPOINT ["java", "-jar", "app.jar"]