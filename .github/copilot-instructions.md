# Spring Boot MCP Tools Framework

Spring Boot application providing a framework for Model Context Protocol (MCP) tools with sample implementations.

## Project Setup Status

- [x] Create project structure
- [x] Configure Spring Boot with MCP tools
- [x] Add sample MCP tools (Calculator, DateTime, String Processing)
- [x] Compile and verify - BUILD SUCCESS
- [x] Create run configuration (Maven, VS Code debug)
- [x] Add comprehensive unit tests
- [x] Document API endpoints

## Technologies
- Spring Boot 3.3.0
- MCP Protocol Framework
- Java 17+
- Maven 3.8.0+
- JUnit 5

## How to Run

### Build the project:
```bash
mvn clean install
```

### Run the application:
```bash
mvn spring-boot:run
```

The app will start on http://localhost:8080

### Run tests:
```bash
mvn test
```

## Available API Endpoints

- `GET /api/ai/tools` - List all available tools
- `POST /api/ai/execute` - Execute a tool with parameters
- `GET /api/ai/health` - Health check endpoint

## Sample MCP Tools Included

1. **Calculator Tools** - add, subtract, multiply, divide
2. **DateTime Tools** - getCurrentDateTime, getTimeDifference
3. **String Processing Tools** - reverseString, getStringLength, toUpperCase, toLowerCase, countOccurrences
