package org.example.controller;

import org.example.service.ApiPingService;
import org.example.service.IpLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiPingService apiPingService;

    @Autowired
    private IpLocationService ipLocationService;

    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> pingEndpoint(@RequestParam String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "URL parameter is required",
                "example", "/api/ping?url=https://httpbin.org/get"
            ));
        }
        
        Map<String, Object> result = apiPingService.pingEndpoint(url);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/health-check")
    public ResponseEntity<Map<String, Object>> healthCheck(@RequestParam String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "URL parameter is required",
                "example", "/api/health-check?url=https://httpbin.org"
            ));
        }
        
        Map<String, Object> result = apiPingService.healthCheck(url);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/endpoint-info")
    public ResponseEntity<Map<String, Object>> getEndpointInfo(@RequestParam String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "URL parameter is required",
                "example", "/api/endpoint-info?url=https://httpbin.org"
            ));
        }
        
        Map<String, Object> result = apiPingService.getEndpointInfo(url);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ping")
    public ResponseEntity<Map<String, Object>> pingEndpointPost(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "URL is required in request body",
                "example", "{ \"url\": \"https://httpbin.org/get\" }"
            ));
        }
        
        Map<String, Object> result = apiPingService.pingEndpoint(url);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ip-location")
    public ResponseEntity<Map<String, Object>> getIpLocation(@RequestParam String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "IP parameter is required",
                "example", "/api/ip-location?ip=8.8.8.8"
            ));
        }
        
        Map<String, Object> result = ipLocationService.getIpLocation(ip);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ip-location")
    public ResponseEntity<Map<String, Object>> getIpLocationPost(@RequestBody Map<String, String> request) {
        String ip = request.get("ip");
        if (ip == null || ip.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "IP is required in request body",
                "example", "{ \"ip\": \"8.8.8.8\" }"
            ));
        }
        
        Map<String, Object> result = ipLocationService.getIpLocation(ip);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test-endpoints")
    public ResponseEntity<Map<String, Object>> getTestEndpoints() {
        return ResponseEntity.ok(Map.of(
            "message", "Available test endpoints for API ping and IP location",
            "endpoints", Map.of(
                "httpbin", Map.of(
                    "get", "https://httpbin.org/get",
                    "post", "https://httpbin.org/post",
                    "status", "https://httpbin.org/status/200",
                    "delay", "https://httpbin.org/delay/2"
                ),
                "jsonplaceholder", Map.of(
                    "posts", "https://jsonplaceholder.typicode.com/posts",
                    "users", "https://jsonplaceholder.typicode.com/users",
                    "comments", "https://jsonplaceholder.typicode.com/comments"
                ),
                "github", Map.of(
                    "api", "https://api.github.com",
                    "users", "https://api.github.com/users/octocat"
                ),
                "sample_ips", Map.of(
                    "google_dns", "8.8.8.8",
                    "cloudflare_dns", "1.1.1.1",
                    "quad9_dns", "9.9.9.9"
                )
            ),
            "usage", Map.of(
                "ping", "/api/ping?url=https://httpbin.org/get",
                "health", "/api/health-check?url=https://httpbin.org",
                "info", "/api/endpoint-info?url=https://httpbin.org",
                "ip_location", "/api/ip-location?ip=8.8.8.8"
            )
        ));
    }
}