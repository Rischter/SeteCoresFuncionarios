# ----------------------------------------------------------------------
# ESTÁGIO 1: Build da aplicação (Compilação)
# Usa o Maven e o JDK 17 (Eclipse Temurin)
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# COPIA OS ARQUIVOS DO PROJETO
# O Build Context é a pasta Funcionarios, então não precisamos repeti-la.
COPY pom.xml .
COPY src ./src

# Executa o build do Maven. O '-B clean install' é mais robusto em ambientes Docker.
RUN mvn -B clean install -DskipTests

# ----------------------------------------------------------------------
# ESTÁGIO 2: Execução (Runtime)
# Usa a imagem Temurin JRE mais estável (17-jre-focal)
# ----------------------------------------------------------------------
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# A porta que a aplicação expõe.
EXPOSE 8080

# Comando para iniciar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]





