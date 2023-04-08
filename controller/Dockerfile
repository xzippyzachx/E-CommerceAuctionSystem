FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY ./target/controller-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","controller-0.0.1-SNAPSHOT.jar"]