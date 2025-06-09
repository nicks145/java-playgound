package org.example.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiPingService {

    private final RestTemplate restTemplate;

    public ApiPingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> pingEndpoint(String url) {
        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("timestamp", LocalDateTime.now());

        try {
            long startTime = System.currentTimeMillis();
            
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            result.put("status", "SUCCESS");
            result.put("httpStatus", response.getStatusCode().value());
            result.put("responseTime", responseTime + "ms");
            result.put("responseLength", response.getBody() != null ? response.getBody().length() : 0);
            result.put("message", "Endpoint is reachable");
            result.put("headers", response.getHeaders().toSingleValueMap());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("status", "ERROR");
            result.put("httpStatus", e.getStatusCode().value());
            result.put("message", "HTTP Error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            result.put("status", "ERROR");
            result.put("message", "Connection failed: " + e.getMessage());
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Unexpected error: " + e.getMessage());
        }

        return result;
    }

    public Map<String, Object> healthCheck(String url) {
        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("timestamp", LocalDateTime.now());

        try {
            long startTime = System.currentTimeMillis();
            
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.HEAD, null, Void.class);

            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            result.put("status", "HEALTHY");
            result.put("httpStatus", response.getStatusCode().value());
            result.put("responseTime", responseTime + "ms");
            result.put("message", "Endpoint is healthy");

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("status", "UNHEALTHY");
            result.put("httpStatus", e.getStatusCode().value());
            result.put("message", "HTTP Error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            result.put("status", "UNHEALTHY");
            result.put("message", "Health check failed: " + e.getMessage());
        } catch (Exception e) {
            result.put("status", "UNHEALTHY");
            result.put("message", "Unexpected error: " + e.getMessage());
        }

        return result;
    }

    public Map<String, Object> getEndpointInfo(String url) {
        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("timestamp", LocalDateTime.now());

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.OPTIONS, null, Void.class);
            
            result.put("status", "SUCCESS");
            result.put("httpStatus", response.getStatusCode().value());
            result.put("allowedMethods", response.getHeaders().get("Allow"));
            result.put("headers", response.getHeaders().toSingleValueMap());
            result.put("message", "Endpoint information retrieved");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Could not retrieve endpoint info: " + e.getMessage());
        }

        return result;
    }
}