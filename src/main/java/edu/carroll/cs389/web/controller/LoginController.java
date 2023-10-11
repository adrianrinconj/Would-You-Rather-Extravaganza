package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceImpl;
import edu.carroll.cs389.web.form.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for handling user login operations.
 */
@Controller
public class LoginController {

    private final UserServiceImpl userServiceImpl;

    /**
     * Constructs the LoginController with the provided user service implementation.
     *
     * @param userServiceImpl The user service implementation.
     */
    public LoginController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Handles the GET request for the login page.
     *
     * @param model The model to be populated for the view.
     * @return The name of the login view.
     */
    @GetMapping("/Login")
    public String LoginGet(Model model) {
        model.addAttribute("LoginForm", new LoginForm());
        // XXX Hard coded for working purposes
        if (userServiceImpl.userLookupUsername("Guest") == null) {
            userServiceImpl.addUser(new User("Guest", "Password"));
        }
        return "Login";
    }

    /**
     * Handles the POST request for user registration.
     *
     * @param model       The model to be populated for the view.
     * @param loginForm   The form containing login details.
     * @param result      The binding result containing validation errors.
     * @param attrs       The redirect attributes.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/Login")
    public String RegisterPost(@Valid @ModelAttribute Model model, LoginForm loginForm, BindingResult result, RedirectAttributes attrs) {
        User loggedUser = userServiceImpl.loginValidation(loginForm.getUsername(), loginForm.getRawPassword());

        if (!result.hasErrors() && loggedUser != null) {
            model.addAttribute("Current User", loggedUser);
            return "WouldYouRatherEntry";
        }

        return "Login";
    }
}
