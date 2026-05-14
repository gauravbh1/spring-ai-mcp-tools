package com.example.springai.tools;

import java.util.HashMap;
import java.util.Map;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

/**
 * MCP Tool: String Processing
 * Provides string manipulation operations for the AI model
 */
@Component
public class StringProcessingTool {

    @McpTool(description = "Reverses the characters in the provided string")
    public Map<String, Object> reverseString(
            @McpToolParam(description = "The string to reverse") String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("original", input);
        result.put("reversed", new StringBuilder(input).reverse().toString());
        return result;
    }

    @McpTool(description = "Returns the length (number of characters) of the provided string")
    public Map<String, Object> getStringLength(
            @McpToolParam(description = "The string to measure") String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", input);
        result.put("length", input.length());
        return result;
    }

    @McpTool(description = "Converts all characters in the string to uppercase")
    public Map<String, Object> toUpperCase(
            @McpToolParam(description = "The string to convert to uppercase") String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("original", input);
        result.put("uppercase", input.toUpperCase());
        return result;
    }

    @McpTool(description = "Converts all characters in the string to lowercase")
    public Map<String, Object> toLowerCase(
            @McpToolParam(description = "The string to convert to lowercase") String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("original", input);
        result.put("lowercase", input.toLowerCase());
        return result;
    }

    @McpTool(description = "Counts how many times a substring appears in the given string")
    public Map<String, Object> countOccurrences(
            @McpToolParam(description = "The string to search in") String input,
            @McpToolParam(description = "The substring to count occurrences of") String substring) {
        Map<String, Object> result = new HashMap<>();
        int count = input.split(substring, -1).length - 1;
        result.put("input", input);
        result.put("substring", substring);
        result.put("occurrences", count);
        return result;
    }
}
