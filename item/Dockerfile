FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY ./target/item-0.0.1-SNAPSHOT.jar /app

EXPOSE 8082

ENTRYPOINT ["java","-jar","item-0.0.1-SNAPSHOT.jar"]