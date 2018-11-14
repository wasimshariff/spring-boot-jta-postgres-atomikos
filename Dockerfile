# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

LABEL maintainer="wasim.shariff@physiciansmutual.com"

ARG JAR_FILE=target/postgres-1.0.0-SNAPSHOT.jar

ADD ${JAR_FILE} MySpringBoot.jar

# Run the jar file
ENTRYPOINT ["java","-jar","MySpringBoot.jar"]