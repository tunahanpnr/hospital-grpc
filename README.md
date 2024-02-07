# Hospital Server - gRPC Spring Boot

## Overview

This project is a sample hospital system implementation of a gRPC (Remote Procedure Call) service using Spring Boot. It demonstrates how to create a simple gRPC server in a Spring Boot application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Project Structure](#project-structure)
- [Running the Application](#running-the-application)

## Prerequisites

Before you begin, ensure you have the following prerequisites:

- Java Development Kit (JDK) 17 or later
- Gradle

For testing via Terminal:
- grpcurl

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/tunahanpnr/hospital-grpc 
    ```

2. Navigate to the project directory:

    ```bash
    cd hospital-grpc
    ```

3. Build the project:

    ```bash
    ./gradlew build
    ```

## Running the Application

To run the gRPC server, execute the following command:
```bash
./gradlew bootRun
```

To test the gRPC server, execute the following command:
```bash
./gradlew test
```

## Init mock data
You can add some mock data to the db using scripts.

- Open a terminal in resources/initmockdata
- Then give the permission for each file
```bash
chmod +x run_all.sh
chmod +x create_hospitals.sh
chmod +x create_patients.sh
chmod +x assign_patients_to_hospitals.sh
```
- Then run the script "run_all.sh"
```bash
./run_all.sh
```
Warning: assigning might not work since patient and hospital IDs are determined by the DB itself.