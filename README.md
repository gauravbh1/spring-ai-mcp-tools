# Spring Boot MCP Tools Framework

A Spring Boot application using **Spring AI's Model Context Protocol (MCP) Server** framework. This project demonstrates how to create MCP tools annotated with `@Tool` and `@ToolParam` that are auto-discovered and made available via Spring AI's MCP endpoints.

## Features

- **Spring Boot 3.3.0** - Latest Spring Boot framework
- **Spring AI MCP Server** - Built on Spring AI's MCP server webmvc starter
- **Auto-Discovery** - MCP tools are automatically discovered via `@Tool` annotations
- **Sample Tools** - Calculator, DateTime, and String Processing tools included
- **MCP Protocol Support** - Full MCP protocol implementation
- **Easy Integration** - Just annotate methods with `@Tool` and parameters with `@ToolParam`

## Prerequisites

- Java 17 or later
- Maven 3.8.0 or later
- Spring Boot 3.3.0 or later
- (Optional) OpenAI API Key for AI integration

## Project Structure

```
src/main/java/com/example/springai/
├── SpringAiMcpToolsApplication.java    # Main application class
├── controller/
│   └── AiController.java               # REST API endpoints
├── service/
│   └── AiService.java                  # AI service that orchestrates tools
└── tools/
    ├── CalculatorTool.java             # Calculator operations
    ├── DateTimeUtilsTool.java          # Date/time operations
    └── StringProcessingTool.java       # String operations
```

## Available Tools

### Calculator Tools
- `add` - Add two numbers
- `subtract` - Subtract two numbers
- `multiply` - Multiply two numbers
- `divide` - Divide two numbers

### DateTime Tools
- `getCurrentDateTime` - Get current date and time
- `getTimeDifference` - Calculate time difference between two timestamps

### String Processing Tools
- `reverseString` - Reverse a string
- `getStringLength` - Get string length
- `toUpperCase` - Convert string to uppercase
- `toLowerCase` - Convert string to lowercase
- `countOccurrences` - Count occurrences of substring

## Building the Project

```bash
mvn clean install
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Get Available Tools
```
GET /api/ai/tools
```

Response:
```json
{
  "add": "Add two numbers: {a: number, b: number}",
  "subtract": "Subtract two numbers: {a: number, b: number}",
  ...
}
```

### Execute Tool
```
POST /api/ai/execute
Content-Type: application/json

{
  "tool": "add",
  "params": {
    "a": 10,
    "b": 5
  }
}
```

Response:
```json
{
  "tool": "add",
  "operation": "add",
  "operands": [10, 5],
  "result": 15
}
```

### Health Check
```
GET /api/ai/health
```

## Configuration

Edit `src/main/resources/application.properties` to configure:
- Server port
- OpenAI API key (if using AI integration)
- Logging levels

## Extending with New Tools

1. Create a new tool class in `src/main/java/com/example/springai/tools/`
2. Annotate with `@Component`
3. Add methods that return `Map<String, Object>`
4. Register the tool in `AiService.executeTool()` method

Example:
```java
@Component
public class MyCustomTool {
    public Map<String, Object> myMethod(String param) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "processed: " + param);
        return result;
    }
}
```

## Testing

Run tests with:
```bash
mvn test
```

## Technologies

- Spring Boot 3.3.0
- Spring AI 1.0.0-M1
- Java 17
- Maven
- Jackson (JSON processing)
- Lombok (optional)

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to add more tools or enhance existing ones. Follow the same pattern as existing tools for consistency.
