package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserService;
import edu.carroll.cs389.web.form.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/Login")

    public String LoginGet(Model model) {
        model.addAttribute("LoginForm", new LoginForm());
        /// XXX Hard coded for working purposes
        User newUser = new User("George", "password");
        userService.addUser(newUser);
        return "Login";
    }

    @PostMapping("/Login")
    public String RegisterPost(@Valid @ModelAttribute Model model, LoginForm loginForm, BindingResult result, RedirectAttributes attrs ) {
        User loggedUser = userService.loginValidation(loginForm.getUsername(), loginForm.getRawPassword());

        if (!result.hasErrors() && loggedUser != null) {
            model.addAttribute("Current User", loggedUser);
        }

        return "WouldYouRatherEntry";
    }
}