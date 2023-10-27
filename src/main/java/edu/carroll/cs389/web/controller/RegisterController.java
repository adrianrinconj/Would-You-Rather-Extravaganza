package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceImpl;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.web.form.LoginForm;
import edu.carroll.cs389.web.form.RegisterLoginForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




// this module is not implemented yet

@Controller
public class RegisterController {
    private static final Logger logInfo = LoggerFactory.getLogger((RegisterController.class));

    private final UserServiceInterface userService;

    public RegisterController(UserServiceInterface userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String RegisterGet(Model model) {
        model.addAttribute("registerLoginForm", new RegisterLoginForm());
        return "Register";
    }

    @PostMapping("/register")
    public String RegisterPost(@Valid @ModelAttribute RegisterLoginForm registerLoginForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            logInfo.debug("result.hasErrors() == true");
            return "Register";
        }
        if (!userService.uniqueUser(registerLoginForm.getUsername())) {
            result.addError(new ObjectError("globalError", "Username must be unique!"));
            return "Register";
        }
        userService.addUser(new User(registerLoginForm.getUsername(),registerLoginForm.getRawPassword()));
        return "redirect:/";
    }
}