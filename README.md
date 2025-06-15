# Java Playground - Expanded Spring Boot API

<p align="center">
    <img src="./images/bean-swing.png" alt="Bean on a swing" width="300" height="300">
    <br/>
    Java daba doo!
</p>

An expanded Spring Boot application with multiple endpoints for API testing, utilities, and more!

## Features

- **🔐 OAuth2 Authentication**: Google and GitHub OAuth2 login support
- **🛡️ Spring Security**: Form-based and OAuth2 authentication
- **🔒 Protected Endpoints**: Secure API access with authentication
- **API Ping Endpoints**: Test and monitor external APIs
- **IP Location Lookup**: Get geographical location information for IP addresses
- **Utility Endpoints**: Various helper functions (Base64 encoding, email validation, etc.)
- **System Information**: Get runtime and system details
- **Interactive Documentation**: Swagger UI integration
- **Health Monitoring**: Built-in actuator endpoints
- **📊 User Dashboard**: Authentication status and protected endpoint access

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 8.14.2 or higher
- OAuth2 provider accounts (Google, GitHub) - optional for OAuth features

### Quick Start

```bash
# Using Gradle wrapper (recommended)
./gradlew bootRun

# Or using Gradle directly
gradle bootRun
```

The application will start on `http://localhost:8080`

### 🔐 OAuth Authentication Setup

For full OAuth2 functionality, see the detailed setup guide: **[OAuth_SETUP.md](OAuth_SETUP.md)**

**Quick Demo**: You can test the authentication immediately using the built-in demo credentials:
- **Admin**: `admin` / `admin123`
- **User**: `demo` / `demo123`

Visit `http://localhost:8080` and try accessing protected endpoints like `/api/ping` - you'll be redirected to the login page.

### Building the Application

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## API Endpoints

> **🔒 Note**: Most API endpoints now require authentication. See the [Authentication](#authentication) section below.

### Public Endpoints (No Authentication Required)

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/` | Welcome message and API overview | `curl http://localhost:8080/` |
| GET | `/greeting` | Personalized greeting | `curl http://localhost:8080/greeting?name=John` |
| GET | `/help` | Complete API documentation | `curl http://localhost:8080/help` |
| GET | `/login` | Login page | Visit in browser |
| GET | `/swagger-ui.html` | Interactive API documentation | Visit in browser |
| GET | `/actuator/health` | Application health status | `curl http://localhost:8080/actuator/health` |

### Protected Endpoints (Authentication Required)

#### Basic Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/status` | Application status and memory info | `curl http://localhost:8080/status` |
| GET | `/echo/{message}` | Echo message with transformations | `curl http://localhost:8080/echo/hello` |
| GET | `/random` | Generate random numbers and values | `curl http://localhost:8080/random` |
| GET | `/dashboard` | User dashboard (after login) | Visit in browser |
| GET | `/user` | Current user information | `curl http://localhost:8080/user` |
| GET | `/auth-status` | Authentication status | `curl http://localhost:8080/auth-status` |

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

## Authentication

The application supports multiple authentication methods:

### 1. OAuth2 Login
- **Google OAuth2**: Click "Continue with Google" on login page
- **GitHub OAuth2**: Click "Continue with GitHub" on login page

### 2. Form-Based Login
Use demo credentials:
- **Admin**: username=`admin`, password=`admin123` (ADMIN, USER roles)
- **User**: username=`demo`, password=`demo123` (USER role)

### 3. Accessing Protected Endpoints

After authentication, you can access protected endpoints:

```bash
# Method 1: Use browser session (login via browser first)
# Then access endpoints directly in browser or use browser's session

# Method 2: Use curl with session cookie
# First login via browser, then extract JSESSIONID cookie
curl -b "JSESSIONID=your-session-id" http://localhost:8080/api/ping?url=https://httpbin.org/get
```

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/login` | Login page with OAuth2 and form options |
| GET | `/logout` | Logout and clear session |
| GET | `/dashboard` | User dashboard with authentication details |
| GET | `/user` | Current user information (JSON) |
| GET | `/auth-status` | Authentication status (JSON) |

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
- Spring Boot Starter Security
- Spring Boot Starter OAuth2 Client
- Spring Boot Starter OAuth2 Resource Server
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Actuator
- SpringDoc OpenAPI (Swagger)
- JUnit 5 (for testing)

## License

This project is a playground/learning application and is provided as-is for educational purposes.