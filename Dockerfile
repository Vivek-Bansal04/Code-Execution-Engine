# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the run.sh script into the container
COPY run.sh /app/run.sh

COPY input.txt /app/input.txt


# Make the run.sh script executable
RUN chmod +x /app/run.sh

# Keep the container running
ENTRYPOINT ["tail", "-f", "/dev/null"]
