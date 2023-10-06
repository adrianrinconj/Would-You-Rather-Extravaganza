package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.service.QuestionService;
import edu.carroll.cs389.service.UserService;
import edu.carroll.cs389.web.form.LoginForm;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService = new UserService();


    @GetMapping("/Login")

    public String LoginGet(Model model) {
        model.addAttribute("LoginForm", new LoginForm());
        return "Login";
    }

    @PostMapping("/Login")
    public String optionsPost(@Valid @ModelAttribute WouldYouRatherForm wouldYouRatherForm, BindingResult result, RedirectAttributes attrs ) {

        if (result.hasErrors()) {

        }

        return "Login";
    }
}