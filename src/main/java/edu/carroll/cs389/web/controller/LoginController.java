package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceImpl;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.web.form.LoginForm;
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

/**
 * This {@code LoginController} class handles user login operations for a web application.
 * It interacts with the {@code UserServiceInterface} to perform operations related to user authentication.
 */
@Controller
public class LoginController {
    private static final Logger logInfo = LoggerFactory.getLogger(LoginController.class);

    private final UserServiceInterface userService;

    /**
     * Creates a new instance of {@code LoginController}.
     * During the initialization, it checks if the "Guest1" user exists, and if not, adds a new user with that username.
     *
     * @param userService The {@code UserServiceInterface} implementation to be used for user-related operations.
     */
    public LoginController(UserServiceInterface userService) {
        this.userService = userService;
        if (userService.userLookupUsername("Guest1") == null) {
            userService.addUser(new User("Guest1", "Password"));
        }
    }

    /**
     * Displays the login page to the user.
     * This method handles GET requests to the root endpoint.
     *
     * @param model The {@code Model} object used to pass data to the view.
     * @return The name of the login view.
     */
    @GetMapping("/")
    public String LoginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "Login";
    }

    /**
     * Processes the user's login form submission.
     * This method handles POST requests to the root endpoint, validating the user's input and attempting to log them in.
     * If validation fails or the login is unsuccessful, the user is shown the login page again with appropriate error messages.
     * On successful login, the user's ID is stored in the session and they are redirected to the next part of the application.
     *
     * @param loginForm The {@code @Valid} annotated {@code LoginForm} object containing the user's login details.
     * @param result    The {@code BindingResult} object containing any validation errors.
     * @param session   The {@code HttpSession} object used to store the logged user's ID on successful login.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            logInfo.debug("User did not provide a valid input: {}", loginForm.getUsername());
            return "Login";
        }

        User loggedUser = userService.loginValidation(loginForm.getUsername(), loginForm.getRawPassword());
        if (loggedUser == null) {
            logInfo.debug("The user {} did not exist", loginForm.getUsername());
            result.addError(new ObjectError("globalError", "No users with your provided credentials exist"));
            return "Login";
        }

        session.setAttribute("loggedUserID", loggedUser.getId());
        return "redirect:/entry";
    }
}
