package org.example.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UtilityService {

    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        systemInfo.put("timestamp", LocalDateTime.now());
        systemInfo.put("timezone", ZoneId.systemDefault().toString());
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        systemInfo.put("osArch", System.getProperty("os.arch"));
        systemInfo.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        systemInfo.put("maxMemory", Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");
        systemInfo.put("totalMemory", Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB");
        systemInfo.put("freeMemory", Runtime.getRuntime().freeMemory() / (1024 * 1024) + " MB");
        
        return systemInfo;
    }

    public Map<String, Object> generateRandomData(int count) {
        Map<String, Object> result = new HashMap<>();
        Random random = new Random();
        
        List<Integer> randomNumbers = random.ints(count, 1, 1000)
                .boxed()
                .collect(Collectors.toList());
        
        List<String> randomStrings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randomStrings.add(generateRandomString(8));
        }
        
        result.put("timestamp", LocalDateTime.now());
        result.put("count", count);
        result.put("randomNumbers", randomNumbers);
        result.put("randomStrings", randomStrings);
        result.put("randomBoolean", random.nextBoolean());
        result.put("randomDouble", Math.round(random.nextDouble() * 100.0) / 100.0);
        
        return result;
    }

    public Map<String, Object> validateEmail(String email) {
        Map<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("timestamp", LocalDateTime.now());
        
        if (email == null || email.trim().isEmpty()) {
            result.put("valid", false);
            result.put("reason", "Email is empty or null");
            return result;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean isValid = email.matches(emailRegex);
        
        result.put("valid", isValid);
        result.put("reason", isValid ? "Valid email format" : "Invalid email format");
        result.put("domain", isValid ? email.substring(email.indexOf("@") + 1) : null);
        result.put("localPart", isValid ? email.substring(0, email.indexOf("@")) : null);
        
        return result;
    }

    public Map<String, Object> encodeBase64(String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("originalText", input);
        result.put("timestamp", LocalDateTime.now());
        
        if (input == null) {
            result.put("encoded", null);
            result.put("error", "Input is null");
        } else {
            String encoded = Base64.getEncoder().encodeToString(input.getBytes());
            result.put("encoded", encoded);
            result.put("originalLength", input.length());
            result.put("encodedLength", encoded.length());
        }
        
        return result;
    }

    public Map<String, Object> decodeBase64(String encoded) {
        Map<String, Object> result = new HashMap<>();
        result.put("encodedText", encoded);
        result.put("timestamp", LocalDateTime.now());
        
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encoded);
            String decoded = new String(decodedBytes);
            result.put("decoded", decoded);
            result.put("success", true);
        } catch (IllegalArgumentException e) {
            result.put("decoded", null);
            result.put("success", false);
            result.put("error", "Invalid Base64 string: " + e.getMessage());
        }
        
        return result;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        
        return sb.toString();
    }

    public Map<String, Object> getCurrentTime(String timezone) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            ZoneId zoneId = timezone != null ? ZoneId.of(timezone) : ZoneId.systemDefault();
            LocalDateTime now = LocalDateTime.now(zoneId);
            
            result.put("timezone", zoneId.toString());
            result.put("dateTime", now);
            result.put("formatted", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            result.put("date", now.toLocalDate());
            result.put("time", now.toLocalTime());
            result.put("dayOfWeek", now.getDayOfWeek());
            result.put("dayOfYear", now.getDayOfYear());
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("error", "Invalid timezone: " + e.getMessage());
            result.put("success", false);
            result.put("availableTimezones", ZoneId.getAvailableZoneIds().stream().limit(10).collect(Collectors.toList()));
        }
        
        return result;
    }
}