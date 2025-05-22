# ----- Stage 1: Build with Maven -----
FROM eclipse-temurin:21 AS build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# ----- Stage 2: Run with JRE -----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
