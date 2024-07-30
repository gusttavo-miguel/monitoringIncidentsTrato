# Utiliza a imagem base do Maven 3.9.2 com JDK 21
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Copia o código-fonte do projeto
COPY src /app/src

# Copia o arquivo pom.xml e outros arquivos de configuração
COPY pom.xml /app

# Define o diretório de trabalho
WORKDIR /app

# Resolve as dependências do projeto
RUN mvn clean install

# Utiliza a imagem base do OpenJDK 21 para executar a aplicação
FROM openjdk:21

# Copia o jar gerado pela fase de construção
COPY --from=build /app/target/monitoringIncidentsTrato-1.0-SNAPSHOT.jar /app/app.jar

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta em que a aplicação será executada
EXPOSE 8080

# Comando para inicialização do contêiner
CMD ["java", "-jar", "app.jar"]