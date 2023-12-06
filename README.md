
<!---
nergizun/nergizun is a ✨ special ✨ repository because its `README.md` (this file) appears on your GitHub profile.
You can click the Preview link to take a look at your changes.
--->


# Appointment Booking System 📅 📌 

This is a simple appointment booking system implemented using Spring Boot, JPA, and Docker.

## Overview

The appointment booking system allows users to create availability slots and book appointments. The system is designed using Spring Boot for backend services, JPA for data persistence, and Docker for easy deployment.

## Setup 🔧

### Prerequisites

Make sure you have the following installed on your machine 📦:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started)

### Running the Application

1. **Clone this repository:**

   ```bash
   git clone https://github.com/nergizun/appointment-booking-system.git
   cd appointment-booking-system
2. **Build and run the application:**

   ```bash
   mvn clean install
   java -jar target/appointment-booking-system.jar

The application will be accessible at http://localhost:8080.

### Docker 🐳 🚢
To run the application using Docker:
1. **Build the Docker image:**
   ```bash
   docker build -t appointment-booking-system .

2. **Run the Docker container:**
   ```bash
   docker run -p 8080:8080 --name appointment-booking-app -d appointment-booking-system





