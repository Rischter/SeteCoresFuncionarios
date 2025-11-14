# ----------------------------------------------------------------------
# ESTÁGIO 1: Build da aplicação
# Usa o Maven e o JDK 17 (Eclipse Temurin) para compilar o projeto.
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# COPIA OS ARQUIVOS DO PROJETO
# Importante: O caminho abaixo usa 'funcionarios' (minúsculas).
# Certifique-se de que a pasta no seu repositório Git tenha sido renomeada para 'funcionarios'.
COPY Funcionarios/pom.xml .
COPY Funcionarios/src ./src

# Executa o build do Maven. O '-DskipTests' é essencial para evitar falhas no deploy.
RUN mvn -e -DskipTests package

# ----------------------------------------------------------------------
# ESTÁGIO 2: Execução
# Usa uma imagem JRE menor (runtime) para rodar o aplicativo de forma eficiente.
# ----------------------------------------------------------------------
FROM eclipse-temurin:17-jre-slim
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build para o ambiente de execução.
COPY --from=build /app/target/*.jar app.jar

# A porta que a aplicação Java expõe.
EXPOSE 8080

# Comando para iniciar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
