# Spring Boot Web Application

This is a simple Spring Boot web application.

## How to Start the Server

To start the application, navigate to the root directory of the project in your terminal and execute the following command:

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

## Accessing Swagger Documentation

This application is configured to auto-generate API documentation using SpringDoc OpenAPI. Once the server is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

This page provides an interactive interface to explore and test the available API endpoints.

## How to Add New APIs

To add new API endpoints to this application, follow these steps:

1.  **Create a new REST Controller or add to an existing one:**
    You can define new API endpoints within a class annotated with `@RestController`. For example, the `App.java` file already contains a basic controller.

    ```java
    // Example: app/src/main/java/org/example/App.java
    @RestController
    public class App {
        // ... existing code ...

        @GetMapping("/new-api-endpoint")
        public String newApi() {
            return "This is a new API endpoint!";
        }
    }
    ```

2.  **Define API Endpoints:**
    Use Spring annotations like `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, etc., to define your API endpoints and their corresponding HTTP methods.

3.  **Swagger Auto-Generation:**
    Thanks to the `springdoc-openapi-starter-webmvc-ui` dependency in `app/build.gradle.kts`, any new `@RestController` endpoints you define will automatically be included in the Swagger documentation. You do not need to write separate Swagger annotations unless you want to add more detailed descriptions, examples, or customize the documentation further.

4.  **Rebuild and Run:**
    After adding new API endpoints, rebuild and restart the application using `./gradlew bootRun` to see your changes reflected and updated in the Swagger UI.