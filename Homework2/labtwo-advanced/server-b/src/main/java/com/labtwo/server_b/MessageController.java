package com.labtwo.server_b;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Value("${task-one-message:Default message}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        return "Service B message: " + message;
    }
}
