FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY ./target/user-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081

ENTRYPOINT ["java","-jar","user-0.0.1-SNAPSHOT.jar"]