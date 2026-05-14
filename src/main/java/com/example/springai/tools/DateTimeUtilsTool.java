package com.example.springai.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

/**
 * MCP Tool: DateTime Utilities
 * Provides date and time operations for the AI model
 */
@Component
public class DateTimeUtilsTool {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @McpTool(description = "Gets the current date and time with various components (year, month, day, hour, minute)")
    public Map<String, Object> getCurrentDateTime() {
        Map<String, Object> result = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        result.put("timestamp", System.currentTimeMillis());
        result.put("datetime", now.format(FORMATTER));
        result.put("year", now.getYear());
        result.put("month", now.getMonthValue());
        result.put("day", now.getDayOfMonth());
        result.put("hour", now.getHour());
        result.put("minute", now.getMinute());
        return result;
    }

    @McpTool(description = "Calculates the time difference between two timestamps in seconds, minutes, and hours")
    public Map<String, Object> getTimeDifference(
            @McpToolParam(description = "Start time in format yyyy-MM-dd HH:mm:ss") String startTime,
            @McpToolParam(description = "End time in format yyyy-MM-dd HH:mm:ss") String endTime) {
        Map<String, Object> result = new HashMap<>();
        try {
            LocalDateTime start = LocalDateTime.parse(startTime, FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endTime, FORMATTER);
            long secondsDiff = java.time.temporal.ChronoUnit.SECONDS.between(start, end);
            long minutesDiff = java.time.temporal.ChronoUnit.MINUTES.between(start, end);
            long hoursDiff = java.time.temporal.ChronoUnit.HOURS.between(start, end);

            result.put("startTime", startTime);
            result.put("endTime", endTime);
            result.put("differenceInSeconds", secondsDiff);
            result.put("differenceInMinutes", minutesDiff);
            result.put("differenceInHours", hoursDiff);
        } catch (Exception e) {
            result.put("error", "Invalid date format. Use format: yyyy-MM-dd HH:mm:ss");
        }
        return result;
    }
}
