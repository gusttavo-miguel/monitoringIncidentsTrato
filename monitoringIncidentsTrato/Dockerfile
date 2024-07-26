# Utiliza a imagem base do Maven 3.9.2 com JDK 21
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e outros arquivos de configuração
COPY pom.xml .

# Resolve as dependências do projeto
RUN mvn dependency:go-offline

# Copia o código-fonte do projeto
COPY src ./src

# Compila o projeto
RUN mvn package -DskipTests

# Utiliza a imagem base do OpenJDK 21 para executar a aplicação
FROM openjdk:21

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado pela fase de construção
COPY --from=build /app/target/monitoringIncidentsTrato-1.0-SNAPSHOT.jar app.jar

# Expõe a porta em que a aplicação será executada
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]