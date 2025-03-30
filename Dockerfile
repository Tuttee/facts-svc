FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/facts-svc-0.0.2.jar app.jar

# Expose the port your application runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]