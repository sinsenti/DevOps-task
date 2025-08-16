FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and config files first (because they rarely change)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies and cache them in this layer
RUN ./gradlew clean build -x test --dry-run

# Copy source files (because more frequently changed)
COPY src src

# Run the real build (only re-runs if source changed)
RUN ./gradlew clean build -x test

FROM eclipse-temurin:24-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
USER spring
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar", "app.jar"]
