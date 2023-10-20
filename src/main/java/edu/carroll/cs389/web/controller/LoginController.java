package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceImpl;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.web.form.LoginForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for handling user login operations.
 */
@Controller
public class LoginController {

    private final UserServiceInterface userService;

    /**
     * Constructs the LoginController with the provided user service implementation.
     *
     * @param userService The user service implementation.
     */
    public LoginController(UserServiceInterface userService) {
        this.userService = userService;
        if (userService.userLookupUsername("Guest1") == null) {
            userService.addUser(new User("Guest1", "Password"));
        }
    }

    /**
     * Handles the GET request for the login page.
     *
     * @param model The model to be populated for the view.
     * @return The name of the login view.
     */
    @GetMapping("/login")
    public String LoginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "Login";
    }

    /**
     * Handles the POST request for user registration.
     *
     * @param loginForm   The form containing login details.
     * @param result      The binding result containing validation errors.
     * @param attrs       The redirect attributes.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "Login";
        }
        User loggedUser = userService.loginValidation(loginForm.getUsername(), loginForm.getRawPassword());
        if (loggedUser == null) {
            result.addError(new ObjectError("globalError", "No users with your provided credentials exist"));
            return "Login";
        }
        session.setAttribute("loggedUserID", loggedUser.getId());
        return "redirect:/";
    }
}
