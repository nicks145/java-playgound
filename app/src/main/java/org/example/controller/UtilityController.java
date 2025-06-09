package org.example.controller;

import org.example.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/utils")
public class UtilityController {

    @Autowired
    private UtilityService utilityService;

    @GetMapping("/system-info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> systemInfo = utilityService.getSystemInfo();
        return ResponseEntity.ok(systemInfo);
    }

    @GetMapping("/random-data")
    public ResponseEntity<Map<String, Object>> generateRandomData(
            @RequestParam(defaultValue = "5") int count) {
        if (count < 1 || count > 100) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Count must be between 1 and 100",
                "provided", count
            ));
        }
        
        Map<String, Object> randomData = utilityService.generateRandomData(count);
        return ResponseEntity.ok(randomData);
    }

    @GetMapping("/validate-email")
    public ResponseEntity<Map<String, Object>> validateEmail(@RequestParam String email) {
        Map<String, Object> validation = utilityService.validateEmail(email);
        return ResponseEntity.ok(validation);
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Map<String, Object>> validateEmailPost(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, Object> validation = utilityService.validateEmail(email);
        return ResponseEntity.ok(validation);
    }

    @GetMapping("/encode-base64")
    public ResponseEntity<Map<String, Object>> encodeBase64(@RequestParam String text) {
        Map<String, Object> result = utilityService.encodeBase64(text);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/encode-base64")
    public ResponseEntity<Map<String, Object>> encodeBase64Post(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        Map<String, Object> result = utilityService.encodeBase64(text);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/decode-base64")
    public ResponseEntity<Map<String, Object>> decodeBase64(@RequestParam String encoded) {
        Map<String, Object> result = utilityService.decodeBase64(encoded);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/decode-base64")
    public ResponseEntity<Map<String, Object>> decodeBase64Post(@RequestBody Map<String, String> request) {
        String encoded = request.get("encoded");
        Map<String, Object> result = utilityService.decodeBase64(encoded);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/current-time")
    public ResponseEntity<Map<String, Object>> getCurrentTime(
            @RequestParam(required = false) String timezone) {
        Map<String, Object> timeInfo = utilityService.getCurrentTime(timezone);
        return ResponseEntity.ok(timeInfo);
    }

    @GetMapping("/help")
    public ResponseEntity<Map<String, Object>> getUtilityHelp() {
        return ResponseEntity.ok(Map.of(
            "message", "Available utility endpoints",
            "endpoints", Map.of(
                "system-info", Map.of(
                    "method", "GET",
                    "path", "/utils/system-info",
                    "description", "Get system information"
                ),
                "random-data", Map.of(
                    "method", "GET",
                    "path", "/utils/random-data?count=10",
                    "description", "Generate random data (count: 1-100)"
                ),
                "validate-email", Map.of(
                    "method", "GET/POST",
                    "path", "/utils/validate-email?email=test@example.com",
                    "description", "Validate email format"
                ),
                "encode-base64", Map.of(
                    "method", "GET/POST",
                    "path", "/utils/encode-base64?text=hello",
                    "description", "Encode text to Base64"
                ),
                "decode-base64", Map.of(
                    "method", "GET/POST",
                    "path", "/utils/decode-base64?encoded=aGVsbG8=",
                    "description", "Decode Base64 to text"
                ),
                "current-time", Map.of(
                    "method", "GET",
                    "path", "/utils/current-time?timezone=UTC",
                    "description", "Get current time in specified timezone"
                )
            ),
            "examples", Map.of(
                "POST_email_validation", Map.of(
                    "url", "/utils/validate-email",
                    "body", "{ \"email\": \"user@example.com\" }"
                ),
                "POST_base64_encode", Map.of(
                    "url", "/utils/encode-base64",
                    "body", "{ \"text\": \"Hello World\" }"
                )
            )
        ));
    }
}