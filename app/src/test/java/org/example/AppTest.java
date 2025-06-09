package org.example;

import org.example.controller.basic.BasicController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void basicGreetingNotNull() {
        BasicController controller = new BasicController();
        assertNotNull(controller.getGreeting("World").get("message"));
    }
}
