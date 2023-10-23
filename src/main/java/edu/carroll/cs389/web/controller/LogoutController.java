package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.User;
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

/**
 * Controller responsible for handling user login operations.
 */
@Controller
public class LogoutController {
    private static final Logger logInfo = LoggerFactory.getLogger((LogoutController.class));


    /**
     * Constructs the logout service with the provided user service implementation.
     *
     */
    public LogoutController() {
    }

    /**
     * Handles the GET request for the login page.
     *
     * @param session The current browser session.
     * @return The name of the login view.
     */
    @GetMapping("/logout")
    public String LoginGet(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
