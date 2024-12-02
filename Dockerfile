FROM maven:3.8.6-openjdk-11-slim AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/flight-reservation-system-0.0.1-SNAPSHOT.jar flight-reservation-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/flight-reservation-system-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080