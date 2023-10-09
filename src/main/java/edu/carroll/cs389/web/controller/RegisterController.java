package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.service.UserService;
import edu.carroll.cs389.web.form.RegisterLoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




// this module is not implemented yet

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/Register")

    public String RegisterGet(Model model) {
        model.addAttribute("registerLoginForm", new RegisterLoginForm());
        return "Register";
    }

    @PostMapping("/Register")
    public String RegisterPost(@Valid @ModelAttribute RegisterLoginForm registerLoginForm, BindingResult result, RedirectAttributes attrs ) {

        if (result.hasErrors()) {

        }

        return "Register";
    }
}