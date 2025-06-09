package org.example.controller;

import org.example.service.ApiPingService;
import org.example.service.IpLocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiPingService apiPingService;

    @MockBean
    private IpLocationService ipLocationService;

    @Test
    public void testPingEndpointWithValidUrl() throws Exception {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("status", "SUCCESS");
        mockResponse.put("url", "https://example.com");
        mockResponse.put("responseTime", "100ms");

        when(apiPingService.pingEndpoint(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/ping")
                .param("url", "https://example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.url").value("https://example.com"));
    }

    @Test
    public void testPingEndpointWithoutUrl() throws Exception {
        mockMvc.perform(get("/api/ping"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("URL parameter is required"));
    }

    @Test
    public void testGetTestEndpoints() throws Exception {
        mockMvc.perform(get("/api/test-endpoints"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.endpoints").exists())
                .andExpect(jsonPath("$.usage").exists())
                .andExpect(jsonPath("$.usage.ip_location").exists());
    }

    @Test
    public void testIpLocationEndpointWithValidIp() throws Exception {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("status", "SUCCESS");
        mockResponse.put("ip", "8.8.8.8");
        
        Map<String, Object> location = new HashMap<>();
        location.put("country", "United States");
        location.put("city", "Mountain View");
        mockResponse.put("location", location);

        when(ipLocationService.getIpLocation(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/ip-location")
                .param("ip", "8.8.8.8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.ip").value("8.8.8.8"))
                .andExpect(jsonPath("$.location.country").value("United States"));
    }

    @Test
    public void testIpLocationEndpointWithoutIp() throws Exception {
        mockMvc.perform(get("/api/ip-location"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("IP parameter is required"));
    }

    @Test
    public void testIpLocationEndpointPostWithValidIp() throws Exception {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("status", "SUCCESS");
        mockResponse.put("ip", "1.1.1.1");
        
        Map<String, Object> location = new HashMap<>();
        location.put("country", "Australia");
        location.put("city", "Sydney");
        mockResponse.put("location", location);

        when(ipLocationService.getIpLocation(anyString())).thenReturn(mockResponse);

        String requestBody = "{\"ip\":\"1.1.1.1\"}";

        mockMvc.perform(post("/api/ip-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.ip").value("1.1.1.1"))
                .andExpect(jsonPath("$.location.country").value("Australia"));
    }

    @Test
    public void testIpLocationEndpointPostWithoutIp() throws Exception {
        String requestBody = "{}";

        mockMvc.perform(post("/api/ip-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("IP is required in request body"));
    }
}