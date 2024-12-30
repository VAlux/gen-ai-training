# Gen AI with Spring Boot and Semantic Kernel

## Description
This is a simple Spring Boot application built with Java and Maven.

## Prerequisites
- Java 22 or higher
- Maven 3.6.0 or higher

## Getting Started

### Clone the repository
```sh
git clone git@github.com:VAlux/gen-ai-training.git
cd gen-ai-training
git checkout task_<task number>
```

### Build the project
```sh
mvn clean install
```

### Run the application
```sh
mvn spring-boot:run
```

The application will start on `http://localhost:9090`.

### API Endpoints

All of the example endpoints can be found in the `.http/` directory in the corresponding `*.http` files.

## Testing

### Run unit tests
```sh
mvn test
```

### Run integration tests
```sh
mvn verify
```

## Configuration
Configuration files are located in the `src/main/resources` directory. You can modify the `application.properties` file to change the default settings.