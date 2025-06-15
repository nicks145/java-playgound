# OAuth Authentication Setup Guide

This guide explains how to set up OAuth authentication in the Java Playground Spring Boot application.

## 🚀 Features Added

- **Spring Security OAuth2 Client** integration
- **Google OAuth2** authentication
- **GitHub OAuth2** authentication
- **Form-based login** with demo credentials
- **Protected API endpoints**
- **User dashboard** with authentication details
- **Thymeleaf templates** for login and dashboard

## 📋 Prerequisites

1. Java 21 or higher
2. Gradle 8.x
3. OAuth2 provider accounts (Google, GitHub)

## 🔧 Configuration

### 1. OAuth2 Provider Setup

#### Google OAuth2 Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable Google+ API
4. Go to "Credentials" → "Create Credentials" → "OAuth 2.0 Client IDs"
5. Set application type to "Web application"
6. Add authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google`
7. Note down Client ID and Client Secret

#### GitHub OAuth2 Setup
1. Go to GitHub Settings → Developer settings → OAuth Apps
2. Click "New OAuth App"
3. Fill in application details:
   - Application name: Java Playground
   - Homepage URL: `http://localhost:8080`
   - Authorization callback URL: `http://localhost:8080/login/oauth2/code/github`
4. Note down Client ID and Client Secret

### 2. Environment Variables

Set the following environment variables or update `application.yml`:

```bash
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret
export GITHUB_CLIENT_ID=your-github-client-id
export GITHUB_CLIENT_SECRET=your-github-client-secret
```

Or update `app/src/main/resources/application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your-actual-google-client-id
            client-secret: your-actual-google-client-secret
          github:
            client-id: your-actual-github-client-id
            client-secret: your-actual-github-client-secret
```

## 🏃‍♂️ Running the Application

1. **Build the application:**
   ```bash
   ./gradlew build
   ```

2. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the application:**
   - Open browser and go to `http://localhost:8080`
   - You'll see the home page with available endpoints
   - Try accessing protected endpoints like `/api/ping?url=https://httpbin.org/get`
   - You'll be redirected to the login page

## 🔐 Authentication Options

### 1. OAuth2 Login
- **Google**: Click "Continue with Google" on login page
- **GitHub**: Click "Continue with GitHub" on login page

### 2. Form Login
Use the demo credentials:
- **Admin**: username=`admin`, password=`admin123`
- **User**: username=`demo`, password=`demo123`

## 🛡️ Security Configuration

### Protected Endpoints
The following endpoints require authentication:
- `/api/**` - All API endpoints
- `/utils/**` - All utility endpoints
- `/status` - System status
- `/echo/**` - Echo endpoints
- `/random` - Random data generator

### Public Endpoints
These endpoints are accessible without authentication:
- `/` - Home page
- `/greeting` - Greeting endpoint
- `/help` - API help
- `/login` - Login page
- `/swagger-ui/**` - Swagger documentation
- `/actuator/health` - Health check

## 📊 Testing Authentication

### 1. Test Protected Endpoints
After logging in, you can access:
- `GET /api/ping?url=https://httpbin.org/get`
- `GET /api/health-check?url=https://httpbin.org`
- `GET /utils/system-info`
- `GET /status`

### 2. Check User Information
- `GET /user` - Returns current user details
- `GET /auth-status` - Returns authentication status
- `GET /dashboard` - User dashboard with links

### 3. API Testing with Authentication
```bash
# First, get session cookie by logging in through browser
# Then use curl with session cookie:
curl -b "JSESSIONID=your-session-id" http://localhost:8080/api/ping?url=https://httpbin.org/get
```

## 🔍 Troubleshooting

### Common Issues

1. **OAuth2 redirect URI mismatch**
   - Ensure redirect URIs in OAuth provider match exactly
   - Check for trailing slashes and protocol (http vs https)

2. **Dependencies not resolved**
   - Run `./gradlew build --refresh-dependencies`
   - Check internet connection for dependency download

3. **Authentication loops**
   - Clear browser cookies and session data
   - Check OAuth2 client credentials

4. **Access denied errors**
   - Verify OAuth2 scopes are correctly configured
   - Check if OAuth app is approved/published

### Debug Logging
Enable debug logging by adding to `application.yml`:
```yaml
logging:
  level:
    org.springframework.security: DEBUG
    org.springframework.security.oauth2: DEBUG
```

## 📁 File Structure

```
app/
├── src/main/java/org/example/
│   ├── config/
│   │   ├── SecurityConfig.java          # Security configuration
│   │   └── AppConfig.java               # Application configuration
│   ├── controller/
│   │   ├── AuthController.java          # Authentication endpoints
│   │   ├── ApiController.java           # API endpoints
│   │   └── UtilityController.java       # Utility endpoints
│   └── App.java                         # Main application class
├── src/main/resources/
│   ├── templates/
│   │   ├── login.html                   # Login page
│   │   └── dashboard.html               # User dashboard
│   └── application.yml                  # Application configuration
└── build.gradle.kts                     # Dependencies and build config
```

## 🎯 Next Steps

1. **Production Setup**:
   - Use HTTPS in production
   - Store secrets in secure vault
   - Configure proper CORS settings

2. **Enhanced Security**:
   - Add JWT token support
   - Implement role-based access control
   - Add rate limiting

3. **User Management**:
   - Add user registration
   - Implement user profiles
   - Add password reset functionality

## 📚 Additional Resources

- [Spring Security OAuth2 Documentation](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
- [Google OAuth2 Setup Guide](https://developers.google.com/identity/protocols/oauth2)
- [GitHub OAuth Apps Documentation](https://docs.github.com/en/developers/apps/building-oauth-apps)