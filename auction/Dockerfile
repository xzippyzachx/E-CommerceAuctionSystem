FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY ./target/auction-0.0.1-SNAPSHOT.jar /app

EXPOSE 8083

ENTRYPOINT ["java","-jar","auction-0.0.1-SNAPSHOT.jar"]