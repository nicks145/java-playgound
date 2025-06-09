# Java Playground - Expanded Spring Boot API

An expanded Spring Boot application with multiple endpoints for API testing, utilities, and more!

## Features

- **API Ping Endpoints**: Test and monitor external APIs
- **IP Location Lookup**: Get geographical location information for IP addresses
- **Utility Endpoints**: Various helper functions (Base64 encoding, email validation, etc.)
- **System Information**: Get runtime and system details
- **Interactive Documentation**: Swagger UI integration
- **Health Monitoring**: Built-in actuator endpoints

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 8.14.2 or higher

### Running the Application

```bash
# Using Gradle wrapper (recommended)
./gradlew bootRun

# Or using Gradle directly
gradle bootRun
```

The application will start on `http://localhost:8080`

### Building the Application

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## API Endpoints

### Basic Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/` | Welcome message and API overview | `curl http://localhost:8080/` |
| GET | `/greeting` | Personalized greeting | `curl http://localhost:8080/greeting?name=John` |
| GET | `/status` | Application status and memory info | `curl http://localhost:8080/status` |
| GET | `/echo/{message}` | Echo message with transformations | `curl http://localhost:8080/echo/hello` |
| GET | `/random` | Generate random numbers and values | `curl http://localhost:8080/random` |
| GET | `/help` | Complete API documentation | `curl http://localhost:8080/help` |

### API Testing Endpoints (`/api/*`)

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/api/ping` | Ping an external API endpoint | `curl "http://localhost:8080/api/ping?url=https://httpbin.org/get"` |
| POST | `/api/ping` | Ping endpoint via POST | `curl -X POST -H "Content-Type: application/json" -d '{"url":"https://httpbin.org/get"}' http://localhost:8080/api/ping` |
| GET | `/api/health-check` | Quick health check of an endpoint | `curl "http://localhost:8080/api/health-check?url=https://httpbin.org"` |
| GET | `/api/endpoint-info` | Get endpoint information (OPTIONS) | `curl "http://localhost:8080/api/endpoint-info?url=https://httpbin.org"` |
| GET | `/api/ip-location` | Get location information for an IP address | `curl "http://localhost:8080/api/ip-location?ip=8.8.8.8"` |
| POST | `/api/ip-location` | Get IP location via POST | `curl -X POST -H "Content-Type: application/json" -d '{"ip":"8.8.8.8"}' http://localhost:8080/api/ip-location` |
| GET | `/api/test-endpoints` | List of test endpoints for trying | `curl http://localhost:8080/api/test-endpoints` |

### Utility Endpoints (`/utils/*`)

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/utils/system-info` | System and runtime information | `curl http://localhost:8080/utils/system-info` |
| GET | `/utils/random-data` | Generate random data | `curl "http://localhost:8080/utils/random-data?count=10"` |
| GET | `/utils/validate-email` | Validate email format | `curl "http://localhost:8080/utils/validate-email?email=test@example.com"` |
| POST | `/utils/validate-email` | Validate email via POST | `curl -X POST -H "Content-Type: application/json" -d '{"email":"test@example.com"}' http://localhost:8080/utils/validate-email` |
| GET | `/utils/encode-base64` | Encode text to Base64 | `curl "http://localhost:8080/utils/encode-base64?text=hello"` |
| GET | `/utils/decode-base64` | Decode Base64 to text | `curl "http://localhost:8080/utils/decode-base64?encoded=aGVsbG8="` |
| GET | `/utils/current-time` | Get current time in timezone | `curl "http://localhost:8080/utils/current-time?timezone=UTC"` |
| GET | `/utils/help` | Utility endpoints documentation | `curl http://localhost:8080/utils/help` |

### Health and Monitoring

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/actuator/health` | Application health status |
| GET | `/actuator/info` | Application information |
| GET | `/swagger-ui.html` | Interactive API documentation |
| GET | `/v3/api-docs` | OpenAPI specification |

## Example Usage

### Testing External APIs

```bash
# Ping Google's homepage
curl "http://localhost:8080/api/ping?url=https://www.google.com"

# Health check for GitHub API
curl "http://localhost:8080/api/health-check?url=https://api.github.com"

# Test JSONPlaceholder API
curl "http://localhost:8080/api/ping?url=https://jsonplaceholder.typicode.com/posts/1"
```

### IP Location Lookup

```bash
# Get location for Google DNS
curl "http://localhost:8080/api/ip-location?ip=8.8.8.8"

# Get location for Cloudflare DNS
curl "http://localhost:8080/api/ip-location?ip=1.1.1.1"

# Get location for Quad9 DNS
curl "http://localhost:8080/api/ip-location?ip=9.9.9.9"
```

### Using Utilities

```bash
# Validate an email address
curl "http://localhost:8080/utils/validate-email?email=user@example.com"

# Encode text to Base64
curl "http://localhost:8080/utils/encode-base64?text=Hello World"

# Get current time in different timezone
curl "http://localhost:8080/utils/current-time?timezone=America/New_York"

# Generate random data
curl "http://localhost:8080/utils/random-data?count=5"
```

### POST Examples

```bash
# Ping endpoint via POST
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"url":"https://httpbin.org/json"}' \
  http://localhost:8080/api/ping

# Get IP location via POST
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"ip":"8.8.8.8"}' \
  http://localhost:8080/api/ip-location

# Validate email via POST
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com"}' \
  http://localhost:8080/utils/validate-email
```

## Response Format

All endpoints return JSON responses with consistent structure:

```json
{
  "status": "SUCCESS|ERROR|HEALTHY|UNHEALTHY",
  "timestamp": "2024-01-01T12:00:00",
  "message": "Description of the result",
  "data": "Additional response data"
}
```

## Development

### Project Structure

```
app/
├── src/main/java/org/example/
│   ├── App.java                    # Main application class
│   ├── config/
│   │   └── AppConfig.java          # Configuration beans
│   ├── controller/
│   │   ├── ApiController.java      # API testing endpoints
│   │   └── UtilityController.java  # Utility endpoints
│   └── service/
│       ├── ApiPingService.java     # API ping functionality
│       ├── IpLocationService.java  # IP geolocation functionality
│       └── UtilityService.java     # Utility functions
└── src/test/java/org/example/
    └── controller/
        └── ApiControllerTest.java  # Unit tests
```

### Adding New Endpoints

1. Create service classes in `service/` package
2. Create controller classes in `controller/` package
3. Add tests in `src/test/java/`
4. Update this README with documentation

## Dependencies

- Spring Boot 3.3.1
- Spring Boot Starter Web
- Spring Boot Starter Actuator
- SpringDoc OpenAPI (Swagger)
- JUnit 5 (for testing)

## License

This project is a playground/learning application and is provided as-is for educational purposes.