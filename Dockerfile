FROM eclipse-temurin:17.0.13_11-jdk

ARG JAR_FILE=build/libs/*.jar

COPY src/main/resources/schema.sql schema.sql
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]