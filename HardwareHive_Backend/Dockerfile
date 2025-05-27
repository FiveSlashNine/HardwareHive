FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY --chown=gradle:gradle gradlew gradlew
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./

RUN chmod +x gradlew

RUN ./gradlew dependencies || true

COPY --chown=gradle:gradle src src

RUN ./gradlew build -x test

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/HardwareHive_Backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
