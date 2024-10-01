# Etapa 1: Construção
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de pom.xml e outros arquivos necessários para resolver as dependências
COPY pom.xml ./
COPY src ./src

# Executa o Maven para limpar e construir o projeto
RUN mvn clean install -DskipTests

# Use a imagem do OpenJDK 17
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR da aplicação para o contêiner
COPY --from=build /app/target/card-service-0.0.1-SNAPSHOT.jar card-service.jar

# Exponha a porta que a aplicação vai usar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "card-service.jar"]
