package edu.carroll.cs389.web.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * {@code LogoutController} is a Spring controller responsible for handling user logout operations.
 * It manages the process of invalidating user sessions to log users out of the application.
 */
@Controller
public class LogoutController {
    private static final Logger logInfo = LoggerFactory.getLogger(LogoutController.class);

    /**
     * Constructs an instance of {@code LogoutController}.
     * This constructor initializes the controller. If there are any initial setup operations required for
     * the logout process, they should be added here.
     */
    public LogoutController() {
        // Initialization code, if any, should go here
    }

    /**
     * Handles the GET request to initiate the logout process.
     * This method invalidates the current user session, effectively logging the user out of the application.
     * After the session is invalidated, the user is redirected to the login page.
     *
     * @param session The current HTTP session associated with the user.
     * @return A redirect instruction to send the user back to the login page.
     */
    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        logInfo.info("Logging out user with session id: {}", session.getId());
        session.invalidate();
        return "redirect:/";
    }
}
