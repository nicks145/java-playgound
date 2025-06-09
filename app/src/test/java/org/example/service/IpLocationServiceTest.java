package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class IpLocationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private IpLocationService ipLocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ipLocationService = new IpLocationService(restTemplate);
    }

    @Test
    void testGetIpLocation_ValidIp_Success() {
        // Arrange
        String validIp = "8.8.8.8";
        String mockResponse = """
            {
                "status": "success",
                "country": "United States",
                "countryCode": "US",
                "region": "CA",
                "regionName": "California",
                "city": "Mountain View",
                "zip": "94043",
                "lat": 37.4056,
                "lon": -122.0775,
                "timezone": "America/Los_Angeles",
                "isp": "Google LLC",
                "org": "Google Public DNS",
                "as": "AS15169 Google LLC",
                "query": "8.8.8.8"
            }
            """;

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);

        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(validIp);

        // Assert
        assertEquals("SUCCESS", result.get("status"));
        assertEquals(validIp, result.get("ip"));
        assertNotNull(result.get("timestamp"));
        assertNotNull(result.get("location"));
        assertNotNull(result.get("isp"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> location = (Map<String, Object>) result.get("location");
        assertEquals("United States", location.get("country"));
        assertEquals("US", location.get("countryCode"));
        assertEquals("Mountain View", location.get("city"));
    }

    @Test
    void testGetIpLocation_InvalidIp_Error() {
        // Arrange
        String invalidIp = "invalid.ip.address";

        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(invalidIp);

        // Assert
        assertEquals("ERROR", result.get("status"));
        assertEquals("Invalid IP address format", result.get("message"));
        assertEquals(invalidIp, result.get("ip"));
    }

    @Test
    void testGetIpLocation_EmptyIp_Error() {
        // Arrange
        String emptyIp = "";

        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(emptyIp);

        // Assert
        assertEquals("ERROR", result.get("status"));
        assertEquals("Invalid IP address format", result.get("message"));
    }

    @Test
    void testGetIpLocation_NullIp_Error() {
        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(null);

        // Assert
        assertEquals("ERROR", result.get("status"));
        assertEquals("Invalid IP address format", result.get("message"));
    }

    @Test
    void testGetIpLocation_ConnectionError() {
        // Arrange
        String validIp = "8.8.8.8";
        when(restTemplate.getForObject(anyString(), eq(String.class)))
            .thenThrow(new ResourceAccessException("Connection timeout"));

        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(validIp);

        // Assert
        assertEquals("ERROR", result.get("status"));
        assertTrue(result.get("message").toString().contains("Connection failed"));
    }

    @Test
    void testGetIpLocation_ApiFailure() {
        // Arrange
        String validIp = "192.168.1.1"; // Private IP that should fail
        String mockResponse = """
            {
                "status": "fail",
                "message": "private range",
                "query": "192.168.1.1"
            }
            """;

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);

        // Act
        Map<String, Object> result = ipLocationService.getIpLocation(validIp);

        // Assert
        assertEquals("ERROR", result.get("status"));
        assertTrue(result.get("message").toString().contains("private range"));
    }
}