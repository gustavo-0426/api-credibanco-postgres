FROM eclipse-temurin:21-jdk AS build

LABEL org.opencontainers.image.authors="gustavoprasca@hotmail.com"

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven ca-certificates && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    update-ca-certificates

WORKDIR /app/api-credibanco-postgres

# Copiar solo pom.xml para cachear dependencias
COPY ../pom.xml .

# Descargar todas las dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY ../src ./src

# Compilar usando dependencias ya descargadas
RUN mvn clean package -DskipTests -B


FROM eclipse-temurin:21-jre-alpine

# Variable de entorno para puerto
ENV SERVER_PORT=

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S credibanco-user && \
    adduser -S -D -H -u 1001 -s /sbin/nologin -G credibanco-user credibanco-user

WORKDIR /home/api-credibanco-postgres

# Copiar JAR y cambiar permisos
COPY --from=build /app/api-credibanco-postgres/target/*.jar ./api-credibanco-postgres.jar
RUN chown credibanco-user:credibanco-user api-credibanco-postgres.jar

# Cambiar a usuario no-root
USER credibanco-user

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java","-jar","./api-credibanco-postgres.jar"]