# Estágio de Build (builder)
# Usamos uma imagem base do Maven que já vem com o JDK, ideal para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner para o estágio de build
WORKDIR /app

# Copia os arquivos de configuração do Maven (pom.xml) ou Gradle (build.gradle, settings.gradle, etc.)
# Isso permite que o Maven/Gradle baixe as dependências primeiro.
# Se você usa Maven:
COPY pom.xml .
# Se você usa Gradle, descomente as linhas abaixo e comente o COPY pom.xml:
# COPY build.gradle .
# COPY settings.gradle .
# COPY gradlew .
# COPY gradle gradle

# Baixa as dependências do projeto.
# Isso aproveita o cache do Docker, então se as dependências não mudarem, este passo é rápido.
# Se você usa Maven:
RUN mvn dependency:go-offline -B

# Se você usa Gradle, descomente e ajuste:
# RUN ./gradlew dependencies --no-daemon

# Copia o código fonte da sua aplicação
COPY src ./src

# Compila a aplicação e gera o JAR executável.
# O '-DskipTests' é opcional aqui; você pode preferir rodar os testes em um estágio anterior do Jenkins.
# Se você usa Maven:
RUN mvn package -DskipTests

# Se você usa Gradle, descomente e ajuste:
# RUN ./gradlew bootJar -x test

# --- Estágio Final (runtime) ---
# Usamos uma imagem JRE mais leve, contendo apenas o Java Runtime Environment (sem o kit de desenvolvimento).
# Isso resulta em uma imagem Docker final menor e mais segura.
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho onde a aplicação será executada
WORKDIR /app

# Copia o JAR construído do estágio 'builder' para o estágio final
# O nome do JAR pode variar (ex: seu-app-0.0.1-SNAPSHOT.jar)
# Ajuste 'target/*.jar' para o caminho e nome corretos do seu JAR se for diferente.
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que sua aplicação Spring Boot usa (padrão é 8080)
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot quando o contêiner iniciar
# As flags -XX:+ExitOnOutOfMemoryError e -Djava.security.egd=file:/dev/./urandom
# são boas práticas para aplicações Spring Boot em contêineres.
ENTRYPOINT ["java", "-jar", "app.jar"]