FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy wrapper files
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Pre-download Gradle and dependencies separately to leverage Docker cache
# If the network fails, only this step fails, not the whole build
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies --no-daemon || ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Add a non-root user for security (Senior-level practice)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]