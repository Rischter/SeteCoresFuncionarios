# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml e src do subdiretório correto
COPY SteCores/funcionarios/funcionarios/pom.xml .
COPY SteCores/funcionarios/funcionarios/src ./src

RUN mvn -q -e -DskipTests package

# Etapa 2: Rodar a aplicação
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
