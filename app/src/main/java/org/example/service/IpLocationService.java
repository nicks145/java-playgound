package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class IpLocationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    // IP address validation pattern
    private static final Pattern IP_PATTERN = Pattern.compile(
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    public IpLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, Object> getIpLocation(String ipAddress) {
        Map<String, Object> result = new HashMap<>();
        result.put("ip", ipAddress);
        result.put("timestamp", LocalDateTime.now());

        // Validate IP address format
        if (!isValidIpAddress(ipAddress)) {
            result.put("status", "ERROR");
            result.put("message", "Invalid IP address format");
            return result;
        }

        try {
            // Using ip-api.com free service (no API key required)
            String apiUrl = "http://ip-api.com/json/" + ipAddress + "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query";
            
            long startTime = System.currentTimeMillis();
            String response = restTemplate.getForObject(apiUrl, String.class);
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            // Parse JSON response
            JsonNode jsonNode = objectMapper.readTree(response);
            
            if ("success".equals(jsonNode.get("status").asText())) {
                result.put("status", "SUCCESS");
                result.put("responseTime", responseTime + "ms");
                
                // Location information
                Map<String, Object> location = new HashMap<>();
                location.put("country", jsonNode.get("country").asText());
                location.put("countryCode", jsonNode.get("countryCode").asText());
                location.put("region", jsonNode.get("region").asText());
                location.put("regionName", jsonNode.get("regionName").asText());
                location.put("city", jsonNode.get("city").asText());
                location.put("zipCode", jsonNode.get("zip").asText());
                location.put("latitude", jsonNode.get("lat").asDouble());
                location.put("longitude", jsonNode.get("lon").asDouble());
                location.put("timezone", jsonNode.get("timezone").asText());
                
                result.put("location", location);
                
                // ISP information
                Map<String, Object> isp = new HashMap<>();
                isp.put("isp", jsonNode.get("isp").asText());
                isp.put("organization", jsonNode.get("org").asText());
                isp.put("as", jsonNode.get("as").asText());
                
                result.put("isp", isp);
                result.put("message", "IP location retrieved successfully");
                
            } else {
                result.put("status", "ERROR");
                result.put("message", jsonNode.has("message") ? jsonNode.get("message").asText() : "Failed to get location data");
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("status", "ERROR");
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

    private boolean isValidIpAddress(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        return IP_PATTERN.matcher(ip.trim()).matches();
    }
}