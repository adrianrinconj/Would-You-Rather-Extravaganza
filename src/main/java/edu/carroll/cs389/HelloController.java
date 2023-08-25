package edu.carroll.cs389;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/Hello")
    public String index() {
        return "hello from Spring Boot!";
    }
}