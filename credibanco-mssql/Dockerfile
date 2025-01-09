FROM maven:3.8.6-openjdk-18-slim AS build

WORKDIR api
COPY ../pom.xml .
## descarga las dependencias
RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
##RUN mvn dependency:go-offline
COPY ../src ./src
RUN mvn clean package -DskipTests



FROM eclipse-temurin:17-jre-alpine

WORKDIR /home/api-credibanco
COPY --from=build /api/target/*.jar ./api-credibanco-sqlserver.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","./api-credibanco-sqlserver.jar"]