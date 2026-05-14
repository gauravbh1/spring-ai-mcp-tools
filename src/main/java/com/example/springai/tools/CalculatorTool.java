package com.example.springai.tools;

import java.util.HashMap;
import java.util.Map;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

/**
 * MCP Tool: Calculator
 * Provides basic mathematical operations for the AI model
 */
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

    @McpTool(description = "Subtracts the second number from the first number")
    public Map<String, Object> subtract(
            @McpToolParam(description = "The minuend (number to subtract from)") double a,
            @McpToolParam(description = "The subtrahend (number to subtract)") double b) {
        Map<String, Object> result = new HashMap<>();
        result.put("operation", "subtract");
        result.put("operands", new double[] { a, b });
        result.put("result", a - b);
        return result;
    }

    @McpTool(description = "Multiplies two numbers and returns the product")
    public Map<String, Object> multiply(
            @McpToolParam(description = "The first number to multiply") double a,
            @McpToolParam(description = "The second number to multiply") double b) {
        Map<String, Object> result = new HashMap<>();
        result.put("operation", "multiply");
        result.put("operands", new double[] { a, b });
        result.put("result", a * b);
        return result;
    }

    @McpTool(description = "Divides the first number by the second number")
    public Map<String, Object> divide(
            @McpToolParam(description = "The dividend (number to divide)") double a,
            @McpToolParam(description = "The divisor (number to divide by)") double b) {
        Map<String, Object> result = new HashMap<>();
        result.put("operation", "divide");
        result.put("operands", new double[] { a, b });
        if (b == 0) {
            result.put("error", "Division by zero");
            result.put("result", null);
        } else {
            result.put("result", a / b);
        }
        return result;
    }
}
