package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceInterface;
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

/**
 * Controller responsible for managing the user registration process.
 * <p>
 * This controller handles the display of the registration form as well as the form submission for user registration.
 * Upon successful registration, the user is redirected to the login page.
 * </p>
 */
@Controller
public class RegisterController {
    private static final Logger logInfo = LoggerFactory.getLogger(RegisterController.class);

    private final UserServiceInterface userService;

    /**
     * Constructs a new {@code RegisterController} with the provided user service.
     *
     * @param userService The user service to be used for user registration and validation.
     */
    public RegisterController(UserServiceInterface userService) {
        this.userService = userService;
    }

    /**
     * Displays the user registration form.
     * <p>
     * This method handles GET requests to the "/register" endpoint, presenting the user with a form to register.
     * A new {@link RegisterLoginForm} object is added to the model to capture the user's registration details.
     * </p>
     *
     * @param model The model to be populated for the view.
     * @return The name of the registration view.
     */
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("registerLoginForm", new RegisterLoginForm());
        return "Register";
    }

    /**
     * Processes the user registration form submission.
     * <p>
     * This method handles POST requests to the "/register" endpoint. It validates the user's input and ensures that
     * the chosen username is unique. If validation fails or the username is not unique, the user is presented with
     * the registration form again along with appropriate error messages. On successful registration, the user is
     * redirected to the login page.
     * </p>
     *
     * @param registerLoginForm The form object containing the user's registration details.
     * @param result            The binding result containing any validation errors.
     * @param session           The HTTP session.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterLoginForm registerLoginForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            logInfo.debug("User registration form contains errors.");
            return "Register";
        }

        if (!userService.uniqueUser(registerLoginForm.getUsername())) {
            logInfo.debug("Username {} is not unique.", registerLoginForm.getUsername());
            result.addError(new ObjectError("globalError", "Username must be unique!"));
            return "Register";
        }

        userService.addUser(new User(registerLoginForm.getUsername(), registerLoginForm.getRawPassword()));
        return "redirect:/";
    }
}
