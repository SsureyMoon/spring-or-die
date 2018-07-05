FROM openjdk:8

RUN apt-get update && \
    apt-get install apt-transport-https

WORKDIR /opt/service

RUN ./gradlew init
