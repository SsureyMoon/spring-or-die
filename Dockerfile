FROM openjdk:8

RUN apt-get update && \
    apt-get install apt-transport-https

WORKDIR /opt/service

COPY ./gradle /opt/service/gradle
COPY ./gradlew /opt/service/gradlew

RUN ./gradlew init
