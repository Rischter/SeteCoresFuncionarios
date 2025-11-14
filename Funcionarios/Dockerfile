# ----------------------------------------------------------------------
# ESTÁGIO 1: Build da aplicação (Compilação)
# Usa o Maven e o JDK 17 (Eclipse Temurin)
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# COPIA OS ARQUIVOS DO PROJETO
# O caminho 'funcionarios' deve ser o nome exato da sua pasta no repositório.
COPY Funcionarios/pom.xml .
COPY Funcionarios/src ./src

# Executa o build do Maven. O '-DskipTests' é essencial.
RUN mvn -e -DskipTests package

# ----------------------------------------------------------------------
# ESTÁGIO 2: Execução (Runtime)
# CORRIGIDO: Usa a imagem oficial OpenJDK 17-jre-slim.

FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# A porta que a aplicação expõe.
EXPOSE 8080

# Comando para iniciar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]




