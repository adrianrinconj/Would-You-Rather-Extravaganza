package edu.carroll.cs389;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index(@RequestParam(value="name", defaultValue="Student")String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}