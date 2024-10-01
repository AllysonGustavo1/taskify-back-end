FROM ubuntu:latest AS build
LABEL authors="Allyson"

RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM adoptopenjdk/openjdk11:alpine

EXPOSE 8080

COPY --from=build /target/taskify-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]