FROM eclipse-temurin:21-jdk AS build

LABEL org.opencontainers.image.authors="gustavoprasca@hotmail.com"

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven ca-certificates && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    update-ca-certificates

WORKDIR /app/credibanco-api-postgres

# Copiar solo pom.xml para cachear dependencias
COPY ../pom.xml .

# Descargar todas las dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY ../src ./src

# Compilar usando dependencias ya descargadas
RUN mvn clean package -DskipTests -B


FROM eclipse-temurin:21-jre-alpine

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S credibanco-user && \
    adduser -S -D -H -u 1001 -s /sbin/nologin -G credibanco-user credibanco-user

WORKDIR /home/credibanco-api-postgres

# Copiar JAR y cambiar permisos
COPY --from=build /app/credibanco-api-postgres/target/*.jar ./credibanco-api-postgres.jar
RUN chown credibanco-user:credibanco-user credibanco-api-postgres.jar

# Cambiar a usuario no-root
USER credibanco-user

EXPOSE 9091
ENTRYPOINT ["java","-jar","./credibanco-api-postgres.jar"]