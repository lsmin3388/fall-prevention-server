# syntax=docker/dockerfile:1.3

# 1st Stage: Build Stage
FROM gradle:7.6.0-jdk17 AS build
ARG MODULE_NAME=fall-prevention
ENV MAIN_MODULE=${MODULE_NAME}
ENV MAIN_BOOTSTRAP_MODULE=${MAIN_MODULE}-bootstrap
ENV MAIN_SECURITY_MODULE=${MAIN_MODULE}-security
ENV MAIN_CORE_MODULE=${MAIN_MODULE}-core
ENV MAIN_API_MODULE=${MAIN_MODULE}-api
ENV MAIN_COMMON_MODULE=${MAIN_MODULE}-common

WORKDIR /gradle/project

# Copy only the build scripts first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts ./
COPY ${MAIN_BOOTSTRAP_MODULE}/build.gradle.kts ${MAIN_BOOTSTRAP_MODULE}/
COPY ${MAIN_SECURITY_MODULE}/build.gradle.kts ${MAIN_SECURITY_MODULE}/
COPY ${MAIN_CORE_MODULE}/build.gradle.kts ${MAIN_CORE_MODULE}/
COPY ${MAIN_API_MODULE}/build.gradle.kts ${MAIN_API_MODULE}/
COPY ${MAIN_COMMON_MODULE}/build.gradle.kts ${MAIN_COMMON_MODULE}/

# Cache Gradle dependencies to speed up the build
RUN --mount=type=cache,target=/root/.gradle \
    gradle dependencies --no-daemon

# Copy the rest of the application code
COPY . .

# Build the application using the cached dependencies
RUN --mount=type=cache,target=/root/.gradle \
    gradle bootJar -x test --no-daemon --parallel

# 2nd Stage: Runtime Stage
FROM openjdk:17
ARG MODULE_NAME=fall-prevention
ENV MAIN_MODULE=${MODULE_NAME}
ENV MAIN_BOOTSTRAP_MODULE=${MAIN_MODULE}-bootstrap

WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /gradle/project/${MAIN_BOOTSTRAP_MODULE}/build/libs/*.jar ./app.jar

# Run the application
ENTRYPOINT ["java", "-Duser.timezone=GMT+9", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod-profile", "-jar", "app.jar"]
