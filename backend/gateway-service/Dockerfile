FROM eclipse-temurin:17-jdk

LABEL authors="EgorCharntsev"

WORKDIR /app

COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]