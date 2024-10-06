# Use OpenJDK 23 as the base image
FROM openjdk:23-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven POM file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Build the application
RUN mvn clean package -DskipTests

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app/target/core-0.0.1-SNAPSHOT.jar"]