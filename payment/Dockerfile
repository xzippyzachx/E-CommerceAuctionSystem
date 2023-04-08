FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY ./target/payment-0.0.1-SNAPSHOT.jar /app

EXPOSE 8084

ENTRYPOINT ["java","-jar","payment-0.0.1-SNAPSHOT.jar"]