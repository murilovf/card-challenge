# Base image
FROM openjdk:23-jdk-slim as base

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo jar para o container
COPY target/card-challenge-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]