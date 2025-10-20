FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

LABEL org.opencontainers.image.authors="gustavoprasca@hotmail.com"
WORKDIR api
COPY ../pom.xml .
## descarga las dependencias
RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
##RUN mvn dependency:go-offline
COPY ../src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

WORKDIR /home/api-credibanco
COPY --from=build /api/target/*.jar ./api-credibanco-postgres.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","./api-credibanco-postgres.jar"]