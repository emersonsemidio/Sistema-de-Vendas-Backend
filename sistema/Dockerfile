# Aqui começa a construção da imagem
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalação das dependências
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

# Permissões de execução
RUN chmod +x mvnw

# parte em que gera o jar no target
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Aqui você copia o jar gerado para a imagem final
COPY --from=builder /app/target/sistema-0.0.1-SNAPSHOT.jar app.jar

# Aqui é a porta do render
EXPOSE 10000

# Aqui você define a porta do Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]

