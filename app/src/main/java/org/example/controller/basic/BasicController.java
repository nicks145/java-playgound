package org.example.controller.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Basic API endpoints previously located in the main {@code App} class.
 */
@RestController
@RequestMapping
public class BasicController {

    private static final long START_TIME = System.currentTimeMillis();

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the Java Playground API!");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "2.0");
        response.put("endpoints", Map.of(
                "greeting", "/greeting",
                "status", "/status",
                "api", "/api/*",
                "utils", "/utils/*",
                "docs", "/swagger-ui.html"
        ));
        response.put("description", "An expanded Spring Boot playground with API ping, utilities, and more!");
        return response;
    }

    @GetMapping("/greeting")
    public Map<String, Object> getGreeting(@RequestParam(defaultValue = "World") String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello " + name + "!");
        response.put("timestamp", LocalDateTime.now());
        response.put("personalized", !"World".equals(name));
        return response;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> status = new HashMap<>();

        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        status.put("uptimeMs", System.currentTimeMillis() - START_TIME);
        status.put("memory", Map.of(
                "total", runtime.totalMemory() / (1024 * 1024) + " MB",
                "free", runtime.freeMemory() / (1024 * 1024) + " MB",
                "used", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + " MB"
        ));
        status.put("processors", runtime.availableProcessors());

        return status;
    }

    @GetMapping("/echo/{message}")
    public Map<String, Object> echo(@PathVariable String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("original", message);
        response.put("reversed", new StringBuilder(message).reverse().toString());
        response.put("uppercase", message.toUpperCase());
        response.put("lowercase", message.toLowerCase());
        response.put("length", message.length());
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    @GetMapping("/random")
    public Map<String, Object> getRandom() {
        Random random = new Random();
        Map<String, Object> response = new HashMap<>();

        response.put("randomInt", random.nextInt(1000));
        response.put("randomDouble", Math.round(random.nextDouble() * 100.0) / 100.0);
        response.put("randomBoolean", random.nextBoolean());
        response.put("timestamp", LocalDateTime.now());

        return response;
    }

    @GetMapping("/help")
    public Map<String, Object> getHelp() {
        return Map.of(
                "message", "Java Playground API - Available Endpoints",
                "basic_endpoints", Map.of(
                        "home", "GET /",
                        "greeting", "GET /greeting?name=YourName",
                        "status", "GET /status",
                        "echo", "GET /echo/{message}",
                        "random", "GET /random",
                        "help", "GET /help"
                ),
                "api_endpoints", Map.of(
                        "ping", "GET /api/ping?url=https://example.com",
                        "health_check", "GET /api/health-check?url=https://example.com",
                        "test_endpoints", "GET /api/test-endpoints"
                ),
                "utility_endpoints", Map.of(
                        "system_info", "GET /utils/system-info",
                        "random_data", "GET /utils/random-data?count=5",
                        "validate_email", "GET /utils/validate-email?email=test@example.com",
                        "encode_base64", "GET /utils/encode-base64?text=hello",
                        "current_time", "GET /utils/current-time?timezone=UTC"
                ),
                "documentation", Map.of(
                        "swagger_ui", "/swagger-ui.html",
                        "api_docs", "/v3/api-docs"
                )
        );
    }
}
