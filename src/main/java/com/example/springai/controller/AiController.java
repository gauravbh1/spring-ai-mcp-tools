package com.example.springai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for AI and MCP Tools
 * Spring AI MCP Server handles tool endpoints automatically
 */
@RestController
@RequestMapping("/api")
public class AiController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Spring AI MCP Tools is running");
    }

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("Spring AI MCP Server - MCP tools are auto-discovered and available");
    }
}
