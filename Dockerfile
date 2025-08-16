FROM eclipse-temurin:24-jdk AS build

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:24-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
USER spring
ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "app.jar"]
