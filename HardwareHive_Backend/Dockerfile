FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x gradlew
RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/HardwareHive_Backend-0.0.1-SNAPSHOT.jar"]
