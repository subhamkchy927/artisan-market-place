# Use Amazon Corretto 17 image from Docker Hub
FROM amazoncorretto:17

# Copy  built Spring Boot JAR
COPY target/*.jar app.jar

# Port Spring Boot app runs on
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
