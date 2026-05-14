package com.example.springai;

import com.example.springai.tools.CalculatorTool;
import com.example.springai.tools.DateTimeUtilsTool;
import com.example.springai.tools.StringProcessingTool;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringAiMcpToolsApplicationTests {

    private CalculatorTool calculatorTool;
    private DateTimeUtilsTool dateTimeUtilsTool;
    private StringProcessingTool stringProcessingTool;

    @BeforeEach
    void setUp() {
        calculatorTool = new CalculatorTool();
        dateTimeUtilsTool = new DateTimeUtilsTool();
        stringProcessingTool = new StringProcessingTool();
    }

    @Test
    void testCalculatorAdd() {
        Map<String, Object> result = calculatorTool.add(10, 5);
        assertEquals(15.0, result.get("result"));
        assertEquals("add", result.get("operation"));
    }

    @Test
    void testCalculatorSubtract() {
        Map<String, Object> result = calculatorTool.subtract(10, 5);
        assertEquals(5.0, result.get("result"));
        assertEquals("subtract", result.get("operation"));
    }

    @Test
    void testCalculatorMultiply() {
        Map<String, Object> result = calculatorTool.multiply(10, 5);
        assertEquals(50.0, result.get("result"));
        assertEquals("multiply", result.get("operation"));
    }

    @Test
    void testCalculatorDivide() {
        Map<String, Object> result = calculatorTool.divide(10, 5);
        assertEquals(2.0, result.get("result"));
        assertEquals("divide", result.get("operation"));
    }

    @Test
    void testCalculatorDivideByZero() {
        Map<String, Object> result = calculatorTool.divide(10, 0);
        assertNotNull(result.get("error"));
        assertEquals("Division by zero", result.get("error"));
    }

    @Test
    void testStringReversal() {
        Map<String, Object> result = stringProcessingTool.reverseString("hello");
        assertEquals("olleh", result.get("reversed"));
    }

    @Test
    void testStringLength() {
        Map<String, Object> result = stringProcessingTool.getStringLength("hello");
        assertEquals(5, result.get("length"));
    }

    @Test
    void testToUpperCase() {
        Map<String, Object> result = stringProcessingTool.toUpperCase("hello");
        assertEquals("HELLO", result.get("uppercase"));
    }

    @Test
    void testToLowerCase() {
        Map<String, Object> result = stringProcessingTool.toLowerCase("HELLO");
        assertEquals("hello", result.get("lowercase"));
    }

    @Test
    void testCountOccurrences() {
        Map<String, Object> result = stringProcessingTool.countOccurrences("hello world hello", "hello");
        assertEquals(2, result.get("occurrences"));
    }

    @Test
    void testGetCurrentDateTime() {
        Map<String, Object> result = dateTimeUtilsTool.getCurrentDateTime();
        assertNotNull(result.get("timestamp"));
        assertNotNull(result.get("datetime"));
        assertNotNull(result.get("year"));
    }
}
