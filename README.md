# Spring Boot MCP Tools Framework

A Spring Boot application using **Spring AI's Model Context Protocol (MCP) Server** framework. This project demonstrates how to create MCP tools annotated with `@McpTool` and `@McpToolParam` that are auto-discovered and made available via Spring AI's MCP endpoints.

## Features

- **Spring Boot 3.3.0** - Latest Spring Boot framework
- **Spring AI 2.0.0-M2** - Spring AI MCP Server framework with webmvc starter
- **Spring AI Community MCP Annotations** - Proper @McpTool and @McpToolParam annotations from springaicommunity
- **Auto-Discovery** - MCP tools are automatically discovered via `@McpTool` annotations
- **11 Sample Tools** - Calculator (4 tools), DateTime (2 tools), and String Processing (5 tools)
- **MCP Protocol Support** - Full MCP protocol implementation via HTTP endpoint
- **Easy Integration** - Just annotate methods with `@McpTool` and parameters with `@McpToolParam`

## Prerequisites

- Java 17 or later
- Maven 3.8.0 or later

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

## Quick Start & Testing

### Using MCP Inspector (Recommended)

1. Install MCP Inspector globally:
```bash
npm install -g @modelcontextprotocol/inspector
```

2. Run MCP Inspector:
```bash
npx @modelcontextprotocol/inspector@latest
```

3. Configure the MCP server by creating/updating `.mcp.json`:
```json
{
  "servers": {
    "spring-ai-mcp": {
      "url": "http://localhost:8080/mcp",
      "type": "http"
    }
  }
}
```

4. The Inspector will connect to your running Spring Boot application and show all available tools with their parameters.

5. You can test each tool directly in the Inspector interface!

## API Endpoints

### Health Check
```
GET /api/health
```

Response:
```
Spring AI MCP Tools is running
```

### Info Endpoint
```
GET /api/info
```

Response:
```
Spring AI MCP Server - MCP tools are auto-discovered and available
```

## MCP Protocol Endpoint

The MCP tools are accessible via the MCP protocol endpoint:
```
http://localhost:8080/mcp
```

You can test the tools using the MCP Inspector or any MCP client. The endpoint supports the full MCP protocol for tool discovery and execution.
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
- Server port (default: 8080)
- MCP server settings
- Logging levels

## Extending with New Tools

New tools are automatically discovered! Just:

1. Create a new tool class in `src/main/java/com/example/springai/tools/`
2. Annotate the class with `@Component`
3. Add methods annotated with `@McpTool`
4. Annotate parameters with `@McpToolParam`
5. Return `Map<String, Object>` with results

Example:
```java
@Component
public class MyCustomTool {
    @McpTool(description = "My custom tool description")
    public Map<String, Object> myMethod(
            @McpToolParam(description = "My parameter") String param) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "processed: " + param);
        return result;
    }
}
```

The tool will be automatically registered and available through the MCP endpoint!

## Testing

Run tests with:
```bash
mvn test
```

## Dependencies

- **Spring Boot 3.3.0** - Application framework
- **Spring AI 2.0.0-M2** - MCP server framework
- **Spring AI Community MCP Annotations 0.8.0** - @McpTool and @McpToolParam
- **Java 17+** - Runtime
- **Maven 3.8.0+** - Build tool

## Technologies

- Spring Boot 3.3.0
- Spring AI 2.0.0-M2
- Spring AI Community MCP Annotations 0.8.0
- Java 17
- Maven
- Jackson (JSON processing)
- Lombok (optional)

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to add more tools or enhance existing ones. Follow the same pattern as existing tools for consistency.
