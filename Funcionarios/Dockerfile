# ----------------------------------------------------------------------
# ESTÁGIO 1: Build da aplicação (Compilação)
# Usa o Maven e o JDK 17 (Eclipse Temurin)
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# COPIA OS ARQUIVOS DO PROJETO
# CORREÇÃO: Copiando diretamente, pois o Build Context já é a pasta "Funcionarios".
COPY pom.xml .
COPY src ./src

# Executa o build do Maven. O '-DskipTests' é essencial.
RUN mvn -e -DskipTests package

# ----------------------------------------------------------------------
# ESTÁGIO 2: Execução (Runtime)
# CORREÇÃO: Usando a tag JRE mais estável e comprovada.
# ----------------------------------------------------------------------
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# A porta que a aplicação expõe.
EXPOSE 8080

# Comando para iniciar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]




