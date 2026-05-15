# Build an MCP Server with Spring AI and Test It with MCP Inspector

## What is Model Context Protocol?

**Model Context Protocol (MCP)** is an open standard developed by Anthropic for connecting AI systems with external tools and data sources. Think of it as a standardized way to expose your application's capabilities to AI models and clients.

Instead of building custom integration code for each AI system or client, MCP provides:

- A consistent protocol for tool discovery
- Standardized tool invocation contracts
- Built-in support for prompt templates and resources
- HTTP and stdio transport options

Learn more: [Model Context Protocol Documentation](https://modelcontextprotocol.io/)

MCP is quickly becoming the industry standard for AI tooling, used by Claude, VS Code, and many AI platforms.

## Building MCP with Spring AI

In this guide, I'll show how we built a production-ready MCP server using Spring AI and how to test all tools end-to-end using MCP Inspector.

## Why MCP with Spring AI?

If you already use Spring Boot, Spring AI gives you a clean path to MCP:

- **Host an MCP server over HTTP** - No custom protocol or framework needed
- **Auto-discover tools from Spring beans** - Leverage Spring's component scanning
- **Expose tool metadata automatically** - Names, descriptions, parameters without manual wiring
- **Keep business logic in plain Java methods** - No coupling to MCP framework code
- **Reuse across AI systems** - Once exposed as MCP, works with Claude, VS Code, and other MCP clients

### Why MCP Matters

Traditional AI integration requires:
- Custom REST endpoints for each tool
- Custom parameter parsing per tool
- Custom error handling contracts
- Reimplementing everything for each AI platform

MCP standardizes this. Once you expose a tool via MCP, it's discoverable and usable by any MCP-compatible client.

Learn more: [MCP for LLM Applications](https://modelcontextprotocol.io/introduction)

### Our Implementation

In our project, we implemented 11 tools:

- Calculator tools: `add`, `subtract`, `multiply`, `divide`
- DateTime tools: `getCurrentDateTime`, `getTimeDifference`
- String tools: `reverseString`, `getStringLength`, `toUpperCase`, `toLowerCase`, `countOccurrences`

## Tech Stack

- Spring Boot 3.3.0
- Spring AI 2.0.0-M2
- Spring AI MCP Server WebMVC Starter
- Spring AI Community MCP Annotations 0.8.0
- Java 17
- Maven

## Project Dependencies

The critical pieces are:

- `org.springframework.ai:spring-ai-starter-mcp-server-webmvc`
- `org.springaicommunity:mcp-annotations:0.8.0`

The second dependency provides `@McpTool` and `@McpToolParam`, which we use for tool discovery metadata.

## MCP Server Configuration

`application.properties`:

```properties
spring.application.name=spring-ai-mcp-tools
server.port=8080

spring.ai.mcp.server.name=spring-ai-mcp-tools
spring.ai.mcp.server.request-timeout=1h
spring.ai.mcp.server.protocol=STATELESS
spring.ai.mcp.server.port=8080
```

MCP endpoint exposed by the app:

- `http://localhost:8080/mcp`

Health endpoints:

- `GET /api/health`
- `GET /api/info`

## Creating MCP Tools

Each tool is just a Spring bean method with MCP annotations. Here's the pattern:

### Calculator Tool Example

```java
@Component
public class CalculatorTool {

    @McpTool(description = "Adds two numbers and returns the result")
    public Map<String, Object> add(
            @McpToolParam(description = "The first number to add") double a,
            @McpToolParam(description = "The second number to add") double b) {
        Map<String, Object> result = new HashMap<>();
        result.put("operation", "add");
        result.put("operands", new double[] { a, b });
        result.put("result", a + b);
        return result;
    }

    @McpTool(description = "Divides the first number by the second number")
    public Map<String, Object> divide(
            @McpToolParam(description = "The dividend") double a,
            @McpToolParam(description = "The divisor") double b) {
        Map<String, Object> result = new HashMap<>();
        if (b == 0) {
            result.put("error", "Division by zero");
            return result;
        }
        result.put("operation", "divide");
        result.put("operands", new double[] { a, b });
        result.put("result", a / b);
        return result;
    }
}
```

### DateTime Tool Example

```java
@Component
public class DateTimeUtilsTool {

    @McpTool(description = "Gets current date and time with breakdown")
    public Map<String, Object> getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", Instant.now().toEpochMilli());
        result.put("datetime", now.format(DateTimeFormatter.ISO_DATE_TIME));
        result.put("year", now.getYear());
        result.put("month", now.getMonthValue());
        result.put("day", now.getDayOfMonth());
        result.put("hour", now.getHour());
        result.put("minute", now.getMinute());
        return result;
    }
}
```

### String Processing Tool Example

```java
@Component
public class StringProcessingTool {

    @McpTool(description = "Counts occurrences of a substring in a string")
    public Map<String, Object> countOccurrences(
            @McpToolParam(description = "The input string") String input,
            @McpToolParam(description = "The substring to count") String substring) {
        int count = 0;
        int index = 0;
        while ((index = input.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("input", input);
        result.put("substring", substring);
        result.put("count", count);
        return result;
    }

    @McpTool(description = "Converts string to uppercase")
    public Map<String, Object> toUpperCase(
            @McpToolParam(description = "The input string") String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", input);
        result.put("output", input.toUpperCase());
        return result;
    }
}
```

Pattern used across all tools:

1. Add `@Component` to the class so Spring auto-detects it
2. Annotate methods with `@McpTool` and include clear descriptions
3. Annotate parameters with `@McpToolParam` and describe each one
4. Return structured output as `Map<String, Object>` with relevant fields

No manual tool registration is required—Spring AI's auto-configuration discovers and registers all `@McpTool` methods automatically.

## Build and Run

### Main Application Class

The Spring Boot entry point is minimal—Spring AI handles MCP setup automatically:

```java
package com.example.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiMcpToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiMcpToolsApplication.class, args);
    }
}
```

When this app starts, Spring automatically scans for `@McpTool` methods, registers them, and exposes them via MCP.

### Build and Run Commands

```bash
mvn clean install
mvn spring-boot:run
```

Once running, your Spring app hosts the MCP server and all tools are available at `http://localhost:8080/mcp`.

You should see logs indicating the tools have been registered:

```
Registered tools: 11
```

## Example Tool Responses

When MCP clients invoke tools, they receive structured JSON responses:

### Calculator add() response:
```json
{
  "operation": "add",
  "operands": [10, 5],
  "result": 15
}
```

### DateTime getCurrentDateTime() response:
```json
{
  "timestamp": 1715792345000,
  "datetime": "2026-05-15T14:32:25.123456",
  "year": 2026,
  "month": 5,
  "day": 15,
  "hour": 14,
  "minute": 32
}
```

### String countOccurrences() response:
```json
{
  "input": "banana",
  "substring": "an",
  "count": 2
}
```

## Testing Tools with MCP Inspector

### What is MCP Inspector?

MCP Inspector is an open-source tool that provides a UI for testing and debugging MCP servers during development. It's maintained by Anthropic and available on GitHub: [modelcontextprotocol/inspector](https://github.com/modelcontextprotocol/inspector)

Inspector lets you:
- Visually explore all available tools and their parameters
- Call tools with test inputs
- See responses in real-time
- Debug tool behavior and responses
- Validate MCP server configuration

It's the fastest way to iterate on your MCP server during development.

### Installation and Testing

```bash
npm install -g @modelcontextprotocol/inspector
```

#### 2) Start Inspector

```bash
npx @modelcontextprotocol/inspector@latest
```

#### 3) Point it to your MCP endpoint

Use this URL:

- `http://localhost:8080/mcp`

If you are using a config file, an example is:

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

#### 4) Validate tool discovery

You should see all 11 tools listed with descriptions and parameters.

#### 5) Execute sample tool tests

Recommended test cases:

- `add(10, 5)` -> result should be 15
- `divide(10, 2)` -> result should be 5
- `divide(10, 0)` -> verify your error-safe behavior
- `getCurrentDateTime()` -> verify timestamp + formatted values
- `countOccurrences("banana", "an")` -> result should be 2
- `toUpperCase("hello world")` -> `HELLO WORLD`

## Testing with GitHub Copilot

Once your MCP server is running, you can connect it directly to GitHub Copilot for real-world AI integration testing.

### Using with GitHub Copilot

GitHub Copilot supports MCP servers for extended functionality.

1. **Configure Copilot Settings**

   In your VS Code `settings.json`:

   ```json
   {
     "github.copilot.mcp.servers": [
       {
         "name": "spring-ai-tools",
         "url": "http://localhost:8080/mcp"
       }
     ]
   }
   ```

2. **Use in Copilot Chat**

   Open the Copilot Chat panel in VS Code and ask:

   - `@mcp What tools are available?` - Lists all your MCP tools
   - `@mcp Add 50 and 30` - Uses your calculator tool
   - `@mcp Get the current time` - Uses your datetime tool

3. **Use in Code Completions**

   Copilot can suggest code that invokes your MCP tools when you write:

   ```java
   // Ask Copilot: "invoke the string reversal tool"
   // Copilot will suggest calling your toUpperCase or reverseString tool
   ```

### Benefits of MCP Integration with GitHub Copilot

## Common Pitfalls

- Using incorrect annotation packages (for example `@Tool` instead of `@McpTool` in this setup)
- Mismatched Spring AI versions across dependencies
- Assuming custom REST endpoints are needed for tool invocation (MCP handles this)
- Missing tool/param descriptions, which degrades MCP client usability

## Why This Approach Works Well

This architecture gives a fast build-test loop:

- Add a Java method
- Annotate it
- Restart app
- Test instantly in MCP Inspector

It scales from toy examples (calculator/string tools) to enterprise tooling with clear contracts.

## Get the Full Codebase

For the complete working implementation including all 11 tools, MCP server setup, Maven configuration, and test examples, see the repository:

**GitHub Repository:**
- https://github.com/gauravbh1/spring-ai-mcp-tools (public)
- Or the internal repository: https://github01.hclpnp.com/gaurav-bhattacharjee/spring-ai-mcp-tools

Clone either repo and run:
```bash
git clone https://github.com/gauravbh1/spring-ai-mcp-tools.git
cd spring-ai-mcp-tools
mvn clean install
mvn spring-boot:run
```

Then connect MCP Inspector to `http://localhost:8080/mcp` and test all 11 tools.

## MCP Resources and Further Learning

If you want to dive deeper into MCP:

- **Official MCP Docs**: [modelcontextprotocol.io](https://modelcontextprotocol.io/)
- **GitHub Repository**: [modelcontextprotocol/python-sdk](https://github.com/modelcontextprotocol/python-sdk) (Reference implementation)
- **MCP Inspector on GitHub**: [modelcontextprotocol/inspector](https://github.com/modelcontextprotocol/inspector)
- **Spring AI Documentation**: [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/)
- **Anthropic Claude API**: Works natively with MCP servers

## Final Thoughts

Spring AI + MCP is a practical way to expose backend capabilities to AI systems without custom protocol glue code. If your organization already uses Spring Boot, this is one of the simplest paths to production-grade MCP tooling.

As a next step, you can extend this server with domain tools (ticket search, policy retrieval, workflow triggers) and keep the same MCP contract model.

The patterns shown here—annotations, auto-discovery, structured responses—remain consistent whether you have 11 tools or hundreds.
